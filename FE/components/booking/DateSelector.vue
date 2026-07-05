<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { today, getLocalTimeZone, parseDate } from '@internationalized/date'
import type { DateRangeValue } from '~/types/filter';
import type { BookedRange } from '~/types/booking';

const dateRange = defineModel<DateRangeValue>({
  required: true
})

const props = defineProps<{
  minDate?: DateValue
  bookedRanges?: BookedRange[]
}>()

const isDateUnavailable = (date: DateValue) => {
  if (date.compare(effectiveMinDate.value) < 0) {
    return true
  }

  return (
    props.bookedRanges?.some((range) => {
      const checkIn = parseDate(range.checkIn)
      const checkOut = parseDate(range.checkOut)

      return date.compare(checkIn) >= 0 && date.compare(checkOut) < 0
    }) ?? false
  )
}

const effectiveMinDate = computed(() => {
  const todayDate = today(getLocalTimeZone())

  if (!props.minDate) {
    return todayDate
  }

  return props.minDate.compare(todayDate) > 0
    ? props.minDate
    : todayDate
})


</script>

<template>
  <UCard>
    <template #header>
      <h2 class="text-xl font-semibold">
        Choose dates
      </h2>
    </template>

    <div class="flex justify-center">
      <UCalendar
        v-model="dateRange"
        range
        :is-date-unavailable="isDateUnavailable"
        :number-of-months="2"
        size="lg"
        :min-value="props.minDate"
        color="primary"
      />
    </div>
  </UCard>
</template>