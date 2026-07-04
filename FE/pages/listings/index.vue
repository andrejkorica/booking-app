<script setup lang="ts">

import type { Listing } from "~/types/listing";

const sort = ref("top");

const config = useRuntimeConfig();
const route = useRoute();

const listings = ref<Listing[]>([]);
const isLoading = ref(false);

const page = ref(1);
const itemsPerPage = 8;

watch([() => route.fullPath, sort], fetchListings, { immediate: true });

const paginatedListings = computed(() => {
  const start = (page.value - 1) * itemsPerPage;
  return listings.value.slice(start, start + itemsPerPage);
});

async function fetchListings() {
  isLoading.value = true;

  const amenitiesQuery = Array.isArray(route.query.amenities)
    ? route.query.amenities
    : route.query.amenities
      ? [route.query.amenities]
      : undefined;

  try {
    listings.value = await $fetch<Listing[]>(
      `${config.public.apiBase}/listings`,
      {
        query: {
          location: route.query.location,
          checkIn: route.query.checkIn,
          checkOut: route.query.checkOut,
          adults: route.query.adults,
          children: route.query.children,
          rooms: route.query.rooms,
          amenities: amenitiesQuery,
          minPrice: route.query.minPrice,
          maxPrice: route.query.maxPrice,
          rating: route.query.rating,
          sellerId: route.query.sellerId,
          sort: sort.value,
        },
      },
    );

    page.value = 1;
  } catch (error) {
    console.error(error);
    listings.value = [];
  } finally {
    isLoading.value = false;
  }
}

watch(() => route.fullPath, fetchListings, { immediate: true });
</script>

<template>
  <div class="min-h-screen bg-slate-100 text-slate-900">
    <FilteringSearchBar />

    <main class="mx-auto max-w-7xl px-4 pt-10 sm:px-6 lg:px-8">
      <div class="flex flex-col gap-6 lg:flex-row lg:items-start">
        <aside class="w-full lg:w-[280px] lg:shrink-0">
          <FilteringBarSidebar />
        </aside>

        <section class="min-w-0 flex-1 pb-12">
          <div
            class="mb-4 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between"
          >
            <div>
              <h1 class="text-2xl font-bold text-slate-900">Search results</h1>

              <p class="mt-1 text-sm text-slate-500">
                {{ listings.length }} properties found
              </p>
            </div>

            <FilteringSortDropdown v-model="sort" />
          </div>

          <div v-if="isLoading" class="py-12 text-center text-slate-500">
            Loading listings...
          </div>

          <div
            v-else-if="listings.length === 0"
            class="rounded-xl border border-slate-200 bg-white py-12 text-center text-slate-500"
          >
            No listings found.
          </div>

          <div v-else>
            <div class="space-y-5">
              <ListingCard
                v-for="listing in paginatedListings"
                :key="listing.id"
                :listing="listing"
              />
            </div>

            <div class="mt-8 flex justify-center">
              <UPagination
                v-model:page="page"
                :items-per-page="itemsPerPage"
                :total="listings.length"
              />
            </div>
          </div>
        </section>
      </div>
    </main>
  </div>
</template>
