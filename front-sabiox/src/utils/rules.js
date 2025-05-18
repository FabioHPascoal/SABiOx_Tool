import DOMPurify from 'dompurify'
import { emailPattern } from 'src/constants'

/**
 *
 * @param {string|function} [msg]
 * @param {object} [options]
 * @returns {validationFn = val => true | string}
 */
export const requiredRule = (msg, options) => {
  const defaults = {
    allowZero: false,
    allowEmptyString: false,
    ignoreHtmlTags: false,
    isBoolean: false
  }
  const { allowZero, allowEmptyString, ignoreHtmlTags, isBoolean } = Object.assign(defaults, options)

  return val => {
    let errorMsg = typeof msg === 'function' ? msg(val) : msg
    errorMsg ??= 'O campo acima é obrigatório.'

    if (typeof val === 'string') {
      if (ignoreHtmlTags) {
        const allowedTags = Array.isArray(ignoreHtmlTags) ? ignoreHtmlTags : []

        val = DOMPurify.sanitize(val, { ALLOWED_TAGS: allowedTags })
      }
      val = val.trim()
      return (allowEmptyString && val === '') || val.length > 0 || errorMsg
    } else if (typeof val === 'number') {
      return (allowZero && val === 0) || !!val || errorMsg
    } else if (Array.isArray(val)) {
      return val.length > 0 || errorMsg
    } else if (isBoolean === true) {
      return typeof val === 'boolean' || errorMsg
    }

    return !!val || errorMsg
  }
}

/**
 *
 * @param {string|function} [msg]
 * @param {object} [options]
 * @returns {validationFn = val => true | string}
 */
export const validEmailRule = (msg, options) => {
  const defaults = {
    required: true
  }
  const { required } = Object.assign(defaults, options)

  return val => {
    if (!required && !val) return true

    let errorMsg = typeof msg === 'function' ? msg(val) : msg
    errorMsg ??= 'Por favor insira um e-mail válido.'

    return emailPattern.test(val) || errorMsg
  }
}

/**
 *
 * @param {number} min
 * @param {string|function} [msg]
 * @param {object} [options]
 * @returns {validationFn = val => true | string}
 */
export const minValueRule = (min, msg, options) => {
  if (isNaN(min)) {
    console.error(min, 'O valor mínimo não é um número válido.')
    min >>= 0
  }

  const defaults = {
    formatter: (n) => n,
    exclusive: false
  }
  const { formatter, exclusive } = Object.assign(defaults, options)

  return val => {
    let errorMsg = typeof msg === 'function' ? msg(val, min) : msg
    errorMsg ??= `Não é permitido valor inferior${exclusive ? ' ou igual' : ''} a ${formatter(min)}.`

    return (exclusive ? val > min : val >= min) || errorMsg
  }
}

/**
 *
 * @param {number} min
 * @param {string|function} [msg]
 * @param {object} [options]
 * @returns {validationFn = val => true | string}
 */
export const minLengthRule = (min, msg, options) => {
  const defaults = {
    exclusive: false,
    trim: true,
    required: true
  }
  const { exclusive, trim, required } = Object.assign(defaults, options)

  const minValueFn = minValueRule(min, null, { exclusive })

  return val => {
    if (!required && !val) return true

    const minVal = exclusive ? min + 1 : min

    let errorMsg = typeof msg === 'function' ? msg(val, minVal) : msg
    errorMsg ??= Array.isArray(val)
      ? `Mínimo de ${minVal} itens.`
      : `Mínimo de ${minVal} caracteres.`

    if (typeof val !== 'string' && !Array.isArray(val)) {
      console.error(`${val} is neither a string nor an array`)
      val = ''
    }

    const v = Array.isArray(val)
      ? val
      : trim
        ? val.trim()
        : val

    return minValueFn(v.length) === true || errorMsg
  }
}
