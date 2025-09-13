const parseObjectToKeys = (obj, appendFn) => {
  const getPrefix = (prefix, key) =>
    prefix + (prefix ? '[' : '') + key + (prefix ? ']' : '')

  const fromArray = (_arr, prefix) => {
    for (let i = 0; i < _arr.length; i++) {
      const val = _arr[i]
      if (Array.isArray(val)) {
        fromArray(val, getPrefix(prefix, String(i)))
      } else if (val instanceof Object && !(val instanceof Blob)) {
        fromObject(val, getPrefix(prefix, String(i)))
      } else {
        appendFn(getPrefix(prefix, String(i)), val)
      }
    }
  }

  const fromObject = (_obj, prefix = '') => {
    for (const [key, value] of Object.entries(_obj)) {
      if (Array.isArray(value)) {
        fromArray(value, getPrefix(prefix, key))
      } else if (value instanceof Object && !(value instanceof Blob)) {
        fromObject(value, getPrefix(prefix, key))
      } else {
        appendFn(getPrefix(prefix, key), value)
      }
    }
  }

  fromObject(obj)
}

/**
 * Receives an object and returns a formdata object appending every object property on it
 *
 * @param {Object} obj Object
 * @returns {FormData}
 */
export const objectToFormData = (obj) => {
  const formData = new FormData()

  const appendValue = (prefix, value) => {
    if (typeof value === 'undefined') return
    else if (typeof value === 'boolean') value = value ? '1' : '0'
    else if (value === null) value = ''
    formData.append(
      prefix,
      value instanceof File || value instanceof Blob ? value : String(value)
    )
  }

  parseObjectToKeys(obj, appendValue)

  return formData
}

/**
 * @param {Array<unknown>} arr Source Array
 * @param {Function} callback
 * @returns {Object} Object with grouped arrays
 */
export const arrayGroupBy = (arr, callback) => {
  return arr.reduce(function (rv, x) {
    ;(rv[callback(x)] = rv[callback(x)] || []).push(x)
    return rv
  }, {})
}
