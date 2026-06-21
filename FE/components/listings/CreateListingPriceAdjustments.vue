<script setup lang="ts">
export type PriceAdjustment = {
  startDate: string
  endDate: string
  percent: number
}

const priceAdjustments = defineModel<PriceAdjustment[]>({
  required: true
})

const startDateInputs = ref<HTMLInputElement[]>([])
const endDateInputs = ref<HTMLInputElement[]>([])

function openStartPicker(index: number) {
  startDateInputs.value[index]?.showPicker?.()
}

function openEndPicker(index: number) {
  endDateInputs.value[index]?.showPicker?.()
}

function addAdjustment() {
  priceAdjustments.value.push({
    startDate: '',
    endDate: '',
    percent: 0
  })
}

function removeAdjustment(index: number) {
  priceAdjustments.value.splice(index, 1)
}
</script>

<template>
  <div>
    <h3 class="mb-4 text-xl font-bold">
      Seasonal Price Adjustments
    </h3>

    <div class="space-y-4">
      <UCard
        v-for="(adjustment, index) in priceAdjustments"
        :key="index"
      >
        <div class="grid grid-cols-1 gap-4 md:grid-cols-[1fr_1fr_180px_auto] md:items-end">
          <UFormField label="Start Date">
            <div class="relative flex gap-2">
              <input
                :ref="el => {
                  if (el) startDateInputs[index] = el as HTMLInputElement
                }"
                v-model="adjustment.startDate"
                type="date"
                class="absolute inset-0 h-full w-full opacity-0 pointer-events-none"
                tabindex="-1"
              >

              <UInput
                :model-value="adjustment.startDate"
                readonly
                placeholder="Select start date"
                class="flex-1"
              />

              <UButton
                icon="i-lucide-calendar"
                color="neutral"
                variant="soft"
                @click="openStartPicker(index)"
              />
            </div>
          </UFormField>

          <UFormField label="End Date">
            <div class="relative flex gap-2">
              <input
                :ref="el => {
                  if (el) endDateInputs[index] = el as HTMLInputElement
                }"
                v-model="adjustment.endDate"
                type="date"
                class="absolute inset-0 h-full w-full opacity-0 pointer-events-none"
                tabindex="-1"
              >

              <UInput
                :model-value="adjustment.endDate"
                readonly
                placeholder="Select end date"
                class="flex-1"
              />

              <UButton
                icon="i-lucide-calendar"
                color="neutral"
                variant="soft"
                @click="openEndPicker(index)"
              />
            </div>
          </UFormField>

          <UFormField label="Increase %">
            <UInput
              v-model.number="adjustment.percent"
              type="number"
              min="0"
              placeholder="20"
              icon="i-lucide-percent"
            />
          </UFormField>

          <div class="flex items-end">
            <UButton
              icon="i-lucide-trash"
              color="error"
              variant="soft"
              :disabled="priceAdjustments.length === 1"
              @click="removeAdjustment(index)"
            />
          </div>
        </div>
      </UCard>

      <UButton
        label="Add Price Adjustment"
        icon="i-lucide-plus"
        variant="soft"
        color="neutral"
        @click="addAdjustment"
      />
    </div>
  </div>
</template>