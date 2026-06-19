<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { getLocalTimeZone } from '@internationalized/date'
import BookingSteps from '~/components/booking/BookingSteps.vue'
import BookingUnitSelector from '~/components/booking/BookingUnitSelector.vue'
import BookingDateSelector from '~/components/booking/BookingDateSelector.vue'
import BookingPriceSummary from '~/components/booking/BookingPriceSummary.vue'
import { unitTypes } from '~/utils/unitTypes'

definePageMeta({
  layout: 'default',
})

type DateRangeValue = {
  start: DateValue | undefined
  end: DateValue | undefined
}

const route = useRoute()

const step = ref(1)

const unitOptions = unitTypes
const selectedUnit = ref(unitOptions[1]?.value ?? 'double_room')

const dateRange = shallowRef<DateRangeValue>({
  start: undefined,
  end: undefined
})

const selectedUnitData = computed(() => {
  return unitOptions.find(unit => unit.value === selectedUnit.value) ?? unitOptions[0]!
})

const nights = computed(() => {
  if (!dateRange.value.start || !dateRange.value.end) {
    return 0
  }

  const start = dateRange.value.start.toDate(getLocalTimeZone())
  const end = dateRange.value.end.toDate(getLocalTimeZone())
  const diff = end.getTime() - start.getTime()

  return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)))
})

const finalPricePerNight = computed(() => {
  return 0
})

const totalPrice = computed(() => {
  return 0
})

const canContinue = computed(() => {
  return Boolean(selectedUnit.value && nights.value > 0)
})

const checkInLabel = computed(() => {
  if (!dateRange.value.start) return 'Not selected'

  return dateRange.value.start
    .toDate(getLocalTimeZone())
    .toLocaleDateString()
})

const checkOutLabel = computed(() => {
  if (!dateRange.value.end) return 'Not selected'

  return dateRange.value.end
    .toDate(getLocalTimeZone())
    .toLocaleDateString()
})

function goNext() {
  if (!canContinue.value) return

  step.value = 2
}
</script>

<template>
  <div class="min-h-screen bg-slate-50 text-slate-900">
    <UContainer class="py-10">
      <div class="mb-8">
        <h1 class="text-3xl font-bold">
          Complete your booking
        </h1>

        <p class="mt-2 text-slate-600">
          Choose the unit type and dates for your stay.
        </p>
      </div>

      <BookingSteps :step="step" />

      <div class="grid grid-cols-1 gap-8 lg:grid-cols-3">
        <div class="space-y-6 lg:col-span-2">
          <BookingUnitSelector
            v-model="selectedUnit"
            :unit-options="unitOptions"
          />

          <BookingDateSelector v-model="dateRange" />
        </div>

        <BookingPriceSummary
          :selected-unit-label="selectedUnitData.label"
          :check-in="checkInLabel"
          :check-out="checkOutLabel"
          :nights="nights"
          :final-price-per-night="finalPricePerNight"
          :total-price="totalPrice"
          :can-continue="canContinue"
          @continue="goNext"
        />
      </div>
    </UContainer>
  </div>
</template>