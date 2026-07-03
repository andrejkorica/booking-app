<script setup lang="ts">
import type { UserBooking } from "~/types/BookingTypes";

const props = defineProps<{
  booking: UserBooking;
}>();

const emit = defineEmits<{
  viewListing: [listingId: number];
  reviewRequest: [bookingId: number];
  cancel: [bookingId: number];
}>();

const isCancelModalOpen = ref(false);

const statusColor = computed(() => {
  if (props.booking.status === "CONFIRMED") return "success";
  if (props.booking.status === "PENDING") return "warning";
  return "error";
});

const canCancel = computed(() => {
  if (props.booking.status === "CANCELLED") return false;

  const today = new Date();
  today.setHours(0, 0, 0, 0);

  const checkIn = new Date(props.booking.checkIn);
  checkIn.setHours(0, 0, 0, 0);

  const diffDays =
    (checkIn.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

  return diffDays > 1;
});

function openCancelModal() {
  isCancelModalOpen.value = true;
}

function closeCancelModal() {
  isCancelModalOpen.value = false;
}

function confirmCancel() {
  emit("cancel", props.booking.id);
  closeCancelModal();
}
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
          <div
            class="flex flex-col gap-2 sm:flex-row sm:items-start sm:justify-between"
          >
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

          <div
            class="mt-4 grid grid-cols-2 gap-3 text-sm sm:grid-cols-3 md:grid-cols-5"
          >
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

          <UTooltip text="Period of cancellation over" :disabled="canCancel">
            <span class="w-full sm:w-auto">
              <UButton
                label="Cancel"
                icon="i-lucide-x"
                color="error"
                variant="ghost"
                size="sm"
                block
                class="sm:w-auto"
                :disabled="!canCancel"
                @click="openCancelModal"
              />
            </span>
          </UTooltip>
        </div>
      </div>
    </div>
  </UCard>

  <UModal v-model:open="isCancelModalOpen" title="Cancel booking">
    <template #body>
      <p class="text-sm text-slate-600">
        Are you sure you want to cancel this booking?
      </p>

      <p class="mt-2 text-sm text-slate-500">
        Cancellation is only allowed more than one day before check-in.
      </p>
    </template>

    <template #footer>
      <div class="grid w-full grid-cols-1 gap-2 sm:flex sm:justify-end">
        <UButton
          label="Keep booking"
          color="neutral"
          variant="soft"
          block
          class="sm:w-auto"
          @click="closeCancelModal"
        />

        <UButton
          label="Cancel booking"
          icon="i-lucide-x"
          color="error"
          block
          class="sm:w-auto"
          @click="confirmCancel"
        />
      </div>
    </template>
  </UModal>
</template>