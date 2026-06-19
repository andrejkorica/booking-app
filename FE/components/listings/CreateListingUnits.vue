<script setup lang="ts">
export type ListingUnit = {
  type: string
  label: string
  enabled: boolean
  quantity: number
  pricePerNight: number
}

const listingUnits = defineModel<ListingUnit[]>({
  required: true
})

const selectedUnits = computed(() =>
  listingUnits.value.filter((unit: ListingUnit) =>
    unit.enabled &&
    unit.quantity > 0 &&
    unit.pricePerNight > 0
  )
)

const lowestPrice = computed(() => {
  if (!selectedUnits.value.length) {
    return 0
  }

  return Math.min(
    ...selectedUnits.value.map(
      (unit: ListingUnit) => unit.pricePerNight
    )
  )
})

const highestPrice = computed(() => {
  if (!selectedUnits.value.length) {
    return 0
  }

  return Math.max(
    ...selectedUnits.value.map(
      (unit: ListingUnit) => unit.pricePerNight
    )
  )
})
</script>

<template>
  <div>
    <h3 class="mb-4 text-xl font-bold">
      Units and Prices
    </h3>

    <UCard class="mb-8">
      <div class="space-y-4">
        <div
          v-for="unit in listingUnits"
          :key="unit.type"
          class="grid grid-cols-1 gap-3 rounded-xl border border-slate-200 p-4 md:grid-cols-4 md:items-center"
        >
          <UCheckbox
            v-model="unit.enabled"
            :label="unit.label"
          />

          <UInput
            v-model.number="unit.quantity"
            type="number"
            min="0"
            placeholder="How many?"
            :disabled="!unit.enabled"
          />

          <UInput
            v-model.number="unit.pricePerNight"
            type="number"
            min="0"
            icon="i-lucide-euro"
            placeholder="Price per night"
            :disabled="!unit.enabled"
          />

          <p class="text-sm text-slate-500">
            {{ unit.enabled ? 'Available unit type' : 'Not offered' }}
          </p>
        </div>
      </div>
    </UCard>

    <div class="mb-8 rounded-xl bg-slate-50 p-4 text-sm text-slate-600">
      <span v-if="selectedUnits.length">
        Price range:
        <strong class="text-slate-900">
          €{{ lowestPrice }} - €{{ highestPrice }}
        </strong>
      </span>

      <span v-else>
        Add at least one unit with quantity and price.
      </span>
    </div>
  </div>
</template>