import { date } from 'quasar'

export const formatDate = (d, dateFormat, dateMask) => {
  if (!d) return null

  return date.formatDate(
    dateMask
      ? date.extractDate(d, dateMask)
      : new Date(d),
    dateFormat
  )
}

export const prettifyDate = (d, dateMask) => {
  const innerDate = dateMask
    ? date.extractDate(d, dateMask)
    : new Date(d)

  const now = new Date()

  const diffInSeconds = Math.floor((now - innerDate) / 1000)

  const secondsInMinute = 60
  const secondsInHour = secondsInMinute * 60
  const secondsInDay = secondsInHour * 24
  const secondsInMonth = secondsInDay * 30

  if (diffInSeconds < secondsInMinute) {
    return `há ${diffInSeconds} segundos`
  } else if (diffInSeconds < secondsInHour) {
    const minutes = Math.floor(diffInSeconds / secondsInMinute)
    return `há ${minutes} ${minutes === 1 ? 'minuto' : 'minutos'}`
  } else if (diffInSeconds < secondsInDay) {
    const hours = Math.floor(diffInSeconds / secondsInHour)
    return `há ${hours} ${hours === 1 ? 'hora' : 'horas'}`
  } else if (diffInSeconds < secondsInMonth) {
    const days = Math.floor(diffInSeconds / secondsInDay)
    return `há ${days} ${days === 1 ? 'dia' : 'dias'}`
  } else {
    return formatDate(d, 'DD/MM/YYYY', dateMask)
  }
}
