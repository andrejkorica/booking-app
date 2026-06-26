<script setup lang="ts">
import SellerBookingCard from "~/components/booking/SellerBookingCard.vue";
import ListingAvailableUnits from "~/components/listings/ListingAvailableUnits.vue";

definePageMeta({
  layout: "default",
  middleware: "seller-guard",
});

type BookingStatus = "PENDING" | "CONFIRMED" | "CANCELLED";

type UserBooking = {
  id: number;
  listingId: number;
  listingTitle: string;
  listingLocation: string;
  listingImage: string | null;
  unitLabel: string;
  checkIn: string;
  checkOut: string;
  nights: number;
  pricePerNight: number;
  totalPrice: number;
  status: BookingStatus;
  createdAt: string;
};

type ListingUnit = {
  id?: number;
  type: string;
  label: string;
  quantity: number;
  availableQuantity?: number;
  maxGuests?: number;
  pricePerNight: number;
};

const route = useRoute();
const router = useRouter();
const config = useRuntimeConfig();
const toast = useToast();

const bookings = ref<UserBooking[]>([]);
const availableUnits = ref<ListingUnit[]>([]);

const isLoading = ref(false);
const isLoadingAvailableUnits = ref(false);

async function fetchBookings() {
  isLoading.value = true;

  try {
    bookings.value = await $fetch<UserBooking[]>(
      `${config.public.apiBase}/bookings/seller/listings/${route.params.id}/bookings`,
      {
        credentials: "include",
      }
    );
  } catch (error) {
    console.error(error);

    toast.add({
      title: "Error",
      description: "Failed to load listing bookings.",
      color: "error",
    });
  } finally {
    isLoading.value = false;
  }
}

async function fetchAvailableUnits() {
  isLoadingAvailableUnits.value = true;

  try {
    availableUnits.value = await $fetch<ListingUnit[]>(
      `${config.public.apiBase}/listings/${route.params.id}/available-units`,
      {
        credentials: "include",
      }
    );
  } catch (error) {
    console.error(error);

    toast.add({
      title: "Error",
      description: "Failed to load available units.",
      color: "error",
    });
  } finally {
    isLoadingAvailableUnits.value = false;
  }
}

function viewListing(listingId: number) {
  navigateTo(`/listings/${listingId}`);
}

function reviewRequest(bookingId: number) {
  navigateTo(`/bookings/${bookingId}`);
}

onMounted(async () => {
  await Promise.all([fetchBookings(), fetchAvailableUnits()]);
});
</script>

<template>
  <div class="min-h-screen bg-slate-50 text-slate-900">
    <UContainer class="py-10">
      <div class="mb-8 flex items-center justify-between gap-4">
        <div>
          <h1 class="text-3xl font-bold">
            Listing availability
          </h1>

          <p class="mt-2 text-slate-600">
            Review available units and bookings connected to this listing.
          </p>
        </div>

        <UButton
          label="Back"
          icon="i-lucide-arrow-left"
          variant="soft"
          color="neutral"
          @click="router.back()"
        />
      </div>

      <UCard class="mb-6">
        <div
          v-if="isLoadingAvailableUnits"
          class="py-8 text-center text-slate-500"
        >
          Loading available units...
        </div>

        <ListingAvailableUnits
          v-else
          :units="availableUnits"
          title="Available units"
        />
      </UCard>

      <UCard>
        <template #header>
          <div>
            <h2 class="text-xl font-semibold">
              Current bookings
            </h2>

            <p class="mt-1 text-sm text-slate-500">
              Pending and confirmed booking requests for this listing.
            </p>
          </div>
        </template>

        <div v-if="isLoading" class="py-12 text-center text-slate-500">
          Loading bookings...
        </div>

        <div
          v-else-if="bookings.length === 0"
          class="rounded-xl border border-dashed border-slate-300 bg-white py-12 text-center text-slate-500"
        >
          No bookings for this listing yet.
        </div>

        <div v-else class="space-y-4">
          <SellerBookingCard
            v-for="booking in bookings"
            :key="booking.id"
            :booking="booking"
            @view-listing="viewListing"
            @review-request="reviewRequest"
          />
        </div>
      </UCard>
    </UContainer>
  </div>
</template>