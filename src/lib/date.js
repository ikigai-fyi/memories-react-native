export function timeElapsed(date) {
  const currentDate = new Date();
  const givenDate = new Date(date);
  const seconds = Math.floor((currentDate - givenDate) / 1000);

  // Check for "today"
  if (currentDate.toDateString() === givenDate.toDateString()) {
    return "today";
  }

  // Check for "yesterday"
  const yesterday = new Date(currentDate.getTime());
  yesterday.setDate(yesterday.getDate() - 1);
  if (yesterday.toDateString() === givenDate.toDateString()) {
    return "yesterday";
  }

  let interval = seconds / 31536000; // Number of seconds in a year

  if (interval > 1) {
    return `${Math.floor(interval)} ${pluralize("year", interval)} ago`;
  }
  interval = seconds / 2592000; // Number of seconds in a month
  if (interval > 1) {
    return `${Math.floor(interval)} ${pluralize("month", interval)} ago`;
  }
  interval = seconds / 86400; // Number of seconds in a day
  if (interval > 1) {
    return `${Math.floor(interval)} ${pluralize("day", interval)} ago`;
  }
  interval = seconds / 3600; // Number of seconds in an hour
  if (interval > 1) {
    return `${Math.floor(interval)} ${pluralize("hour", interval)} ago`;
  }
  interval = seconds / 60; // Number of seconds in a minute
  if (interval > 1) {
    return `${Math.floor(interval)} ${pluralize("minute", interval)} ago`;
  }
  return `${Math.floor(seconds)} ${pluralize("second", interval)} ago`;
}

function pluralize(word, count) {
  if (count >= 2) {
    return `${word}s`;
  }
  return word;
}
