export function useDateFormat() {
  function formatDate(date: string) {
    if (!date) return "-"

    return new Date(date).toLocaleDateString("en-GB")
  }

  return {
    formatDate,
  }
}