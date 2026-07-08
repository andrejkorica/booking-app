<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from "vue";
import type { Listing } from "~/types/listing";

const props = defineProps<{
  listing: Listing;
}>();

const currentImage = ref(0);
let interval: ReturnType<typeof setInterval> | null = null;

const reviewRating = computed(() => {
  return props.listing.averageRating ?? 0;
});

const reviewCount = computed(() => {
  return props.listing.reviewCount ?? 0;
});

const ratingLabel = computed(() => {
  if (reviewCount.value === 0) return "New";

  if (reviewRating.value >= 4.8) return "Exceptional";
  if (reviewRating.value >= 4.5) return "Excellent";
  if (reviewRating.value >= 4.0) return "Great";
  if (reviewRating.value >= 3.0) return "Good";

  return "Fair";
});

const reviewRatingLabel = computed(() => {
  if (reviewCount.value === 0) return "New";

  return reviewRating.value.toFixed(1);
});

const reviewCountLabel = computed(() => {
  if (reviewCount.value === 0) return "No reviews yet";
  if (reviewCount.value === 1) return "1 review";

  return `${reviewCount.value} reviews`;
});

const priceLabel = computed(() => {
  if (!props.listing.lowestPrice && !props.listing.highestPrice) {
    return "Price not set";
  }

  if (props.listing.lowestPrice === props.listing.highestPrice) {
    return `€ ${props.listing.lowestPrice}`;
  }

  return `€ ${props.listing.lowestPrice} - ${props.listing.highestPrice}`;
});

function startSlideshow() {
  stopSlideshow();

  if (!props.listing.images || props.listing.images.length <= 1) {
    return;
  }

  interval = setInterval(() => {
    currentImage.value = (currentImage.value + 1) % props.listing.images.length;
  }, 3000);
}

function stopSlideshow() {
  if (interval) {
    clearInterval(interval);
    interval = null;
  }
}

onMounted(startSlideshow);
onUnmounted(stopSlideshow);
</script>

<template>
  <NuxtLink
    :to="`/listings/${listing.id}`"
    class="block w-full text-slate-900 no-underline"
  >
    <div
      class="overflow-hidden rounded-2xl bg-white shadow-md transition-all duration-300 hover:-translate-y-1 hover:shadow-xl sm:flex sm:h-[270px]"
    >
      <div
        class="relative h-52 w-full overflow-hidden bg-slate-100 sm:order-2 sm:h-full sm:w-1/2"
      >
        <img
          v-if="listing.images?.length"
          :src="listing.images[currentImage]"
          alt="Listing image"
          class="h-full w-full object-cover"
        />

        <div
          v-else
          class="flex h-full items-center justify-center text-slate-400"
        >
          No image
        </div>
      </div>

      <div class="flex min-w-0 flex-col justify-between p-4 sm:w-1/2 sm:p-6">
        <div>
          <h2
            class="min-w-0 line-clamp-1 break-words text-lg font-bold leading-snug text-slate-800 sm:text-xl"
            :title="listing.title"
          >
            {{ listing.title }}
          </h2>

          <div class="my-2 flex items-center gap-1 text-sm text-slate-500">
            <span>Rating</span>
            <span>(</span>

            <div class="flex items-center gap-0.5">
              <UIcon
                v-for="i in 5"
                :key="i"
                name="i-heroicons-star-solid"
                :class="
                  i <= listing.rating ? 'text-yellow-400' : 'text-slate-200'
                "
                class="h-4 w-4"
              />
            </div>

            <span>)</span>
          </div>

          <div class="mt-1 line-clamp-1 text-sm text-slate-500">
            {{ listing.city || listing.location }}
          </div>

          <div class="mt-3 line-clamp-2 text-sm text-slate-600">
            {{ listing.description }}
          </div>
        </div>

        <div class="mt-4 space-y-3">
          <div class="flex items-center justify-between gap-3">
            <div class="min-w-0 text-sm">
              <span class="font-semibold text-indigo-600">
                {{ ratingLabel }}
              </span>

              <span class="ml-1 text-slate-400">
                ({{ reviewCountLabel }})
              </span>
            </div>

            <div
              class="rounded-lg bg-indigo-600 px-2.5 py-1 text-sm font-bold text-white"
            >
              {{ reviewRatingLabel }}
            </div>
          </div>

          <div class="flex items-center justify-between gap-3">
            <div class="text-sm text-slate-500">Price per night</div>

            <div class="font-bold text-slate-900">
              {{ priceLabel }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </NuxtLink>
</template>

<style scoped>
.no-underline {
  text-decoration: none;
}
</style>
