<script setup lang="ts">
type ListingUnit = {
  id?: number;
  type: string;
  label: string;
  quantity: number;
  maxGuests?: number;
  pricePerNight: number;
};

defineProps<{
  units?: ListingUnit[];
}>();
</script>

<template>
  <div class="mt-10">
    <h3 class="text-xl font-bold mb-4">Available units</h3>

    <div v-if="units?.length" class="space-y-3">
      <div
        v-for="unit in units"
        :key="unit.id ?? unit.type"
        class="flex items-center justify-between gap-4 rounded-xl border border-slate-200 bg-slate-50 p-4"
      >
        <div>
          <p class="font-semibold text-slate-900">
            {{ unit.label }}
          </p>

          <p class="text-sm text-slate-500">{{ unit.quantity }} available</p>

          <p v-if="unit.maxGuests" class="text-sm text-slate-500">
            Up to {{ unit.maxGuests }}
            {{ unit.maxGuests === 1 ? "guest" : "guests" }}
          </p>

          <p v-else class="text-sm text-slate-400 italic">
            Guest capacity not specified
          </p>
        </div>

        <p class="shrink-0 text-lg font-bold text-slate-900">
          €{{ unit.pricePerNight }} / night
        </p>
      </div>
    </div>

    <p v-else class="text-slate-500">No units listed.</p>
  </div>
</template>
