<script setup lang="ts">
import type { UserBooking } from "~/types/booking";

const props = defineProps<{
  booking: UserBooking;
}>();

const emit = defineEmits<{
  viewListing: [listingId: number];
  reviewRequest: [bookingId: number];
  approve: [bookingId: number];
  reject: [bookingId: number];
}>();

const statusColor = computed(() => {
  if (props.booking.status === "CONFIRMED") return "success";
  if (props.booking.status === "PENDING") return "warning";
  return "error";
});

const isPending = computed(() => props.booking.status === "PENDING");
</script>

<template>
  <UCard variant="soft" class="overflow-hidden">
    <div class="flex flex-col gap-4 md:flex-row md:gap-5">
      <img
        v-if="booking.listingImage"
        :src="booking.listingImage"
        :alt="booking.listingTitle"
        class="h-48 w-full rounded-xl object-cover md:h-44 md:w-56"
      />

      <div
        v-else
        class="flex h-48 w-full items-center justify-center rounded-xl bg-slate-100 text-slate-400 md:h-44 md:w-56"
      >
        No image
      </div>

      <div class="flex flex-1 flex-col justify-between">
        <div>
          <div class="flex flex-col gap-2 sm:flex-row sm:items-start sm:justify-between">
            <div>
              <h3 class="text-lg font-semibold leading-snug">
                {{ booking.listingTitle }}
              </h3>

              <p class="mt-1 text-sm text-slate-500">
                {{ booking.listingLocation }}
              </p>
            </div>

            <UBadge :color="statusColor" variant="soft" class="w-fit">
              {{ booking.status }}
            </UBadge>
          </div>

          <div class="mt-4 grid grid-cols-2 gap-3 text-sm sm:grid-cols-3 md:grid-cols-5">
            <div class="rounded-lg bg-white p-3">
              <p class="text-slate-500">Check-in</p>
              <p class="font-medium">{{ booking.checkIn }}</p>
            </div>

            <div class="rounded-lg bg-white p-3">
              <p class="text-slate-500">Check-out</p>
              <p class="font-medium">{{ booking.checkOut }}</p>
            </div>

            <div class="rounded-lg bg-white p-3">
              <p class="text-slate-500">Nights</p>
              <p class="font-medium">{{ booking.nights }}</p>
            </div>

            <div class="rounded-lg bg-white p-3">
              <p class="text-slate-500">Unit</p>
              <p class="font-medium">
                {{ booking.unitLabel || "Standard" }}
              </p>
            </div>

            <div class="col-span-2 rounded-lg bg-white p-3 sm:col-span-1">
              <p class="text-slate-500">Total</p>
              <p class="font-semibold text-slate-900">
                €{{ booking.totalPrice }}
              </p>
            </div>
          </div>
        </div>

        <div class="mt-5 grid grid-cols-1 gap-2 sm:flex sm:flex-wrap sm:gap-3">
          <UButton
            label="View listing"
            icon="i-lucide-eye"
            size="sm"
            color="neutral"
            variant="soft"
            block
            class="sm:w-auto"
            @click="emit('viewListing', booking.listingId)"
          />

          <UButton
            label="Review request"
            icon="i-lucide-file-text"
            size="sm"
            block
            class="bg-indigo-600 text-white hover:bg-indigo-700 sm:w-auto"
            @click="emit('reviewRequest', booking.id)"
          />

          <UButton
            label="Approve"
            icon="i-lucide-check"
            color="success"
            variant="soft"
            size="sm"
            block
            class="sm:w-auto"
            :disabled="!isPending"
            :class="
              isPending
                ? 'transition-colors hover:bg-green-600 hover:text-white'
                : 'cursor-not-allowed bg-slate-200 text-slate-500'
            "
            @click="emit('approve', booking.id)"
          />

          <UButton
            label="Reject"
            icon="i-lucide-x"
            color="error"
            variant="ghost"
            size="sm"
            block
            class="sm:w-auto"
            :disabled="!isPending"
            :class="
              isPending
                ? 'transition-colors hover:bg-red-600 hover:text-white'
                : 'cursor-not-allowed bg-slate-200 text-slate-500'
            "
            @click="emit('reject', booking.id)"
          />
        </div>
      </div>
    </div>
  </UCard>
</template>