export const sizeTypes = {
  KB: 1024,
  MB: 1048576,
  GB: 1073741824
}

export const regexSizeInString = /^(\d+)\s*(KB|MB|GB)$/

/**
 * Converts a base64 string into a Blob object
 * @param {string} fileBase64 Base64 string to be converted
 *
 * @returns {Blob} Converted Blob
 */
export const base64ToBlob = (fileBase64) => {
  return fetch(fileBase64).then(res => res.blob())
}

/**
 * Returns if a file type matches an accepted types string
 * @param {File}   file   File to be tested if matches
 * @param {string} accept Accepted file types (same as 'accept' attribute in input tags)
 */
export const checkFileType = (file, accept) => {
  if (accept === '*') return true
  if (typeof accept === 'string') accept = accept.split(',')

  return accept.some(acceptedType => {
    const parsed = acceptedType.trim().toLowerCase()

    // Se o tipo for .ext (Exemplo: .pdf)
    if (/^\.[-+.\w]+$/.exec(parsed)) {
      return file.name.toLowerCase().endsWith(parsed)
    }

    // Se o tipo for mimetype (Exemplo: application/pdf)
    if (/^\w+\/[-+.\w]+$/.exec(parsed)) {
      return file.type.toLowerCase() === parsed
    }

    // Se o tipo for mimetype/* (Exemplo: image/*)
    if (/^\w+\/\*$/.exec(parsed)) {
      return file.type.slice(0, file.type.indexOf('/')).toLowerCase() === parsed.slice(0, -2)
    }

    return false
  })
}

/**
 * Converts a file upload into a base64 string
 *
 * @param {File} fileUpload File upload object
 *
 * @returns {string} Converted base64 string
 */
export const getFileBase64 = async (fileUpload) => {
  return new Promise((resolve, reject) => {
    if (!fileUpload) {
      return reject()
    }
    const reader = new FileReader()
    reader.readAsDataURL(fileUpload)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

/**
 * Converts a string with its unit of measurement
 *
 * @param {number|string} size Size to be converted string (case-insensitive) or number in bytes
 *
 * Exemplos:
 * - getSizeInBytes(1024)
 * - getSizeInBytes('1 KB')
 * - getSizeInBytes('3 mb')
 * - getSizeInBytes('1024 Gb')
 *
 * @returns {number} Size in bytes
 */
export const getSizeInBytes = (size) => {
  if (typeof size === 'number') return size
  if (typeof size !== 'string') return null

  size = size.toUpperCase()

  const match = regexSizeInString.exec(size)
  if (match === null) return null

  const [, _size, type] = match

  return parseInt(_size) * sizeTypes[type]
}
