<script setup lang="ts">
import { ratingOptions } from "~/constants/filterConstants";
const router = useRouter();
const route = useRoute();

const rating = ref<number | null>(
  typeof route.query.rating === "string" ? Number(route.query.rating) : null,
);

watch(rating, () => {
  router.replace({
    query: {
      ...route.query,
      rating: rating.value ?? undefined,
    },
  });
});


</script>

<template>
  <div>
    <h3 class="mb-3 font-semibold text-slate-900">Rating</h3>

    <USelect
      v-model="rating"
      :items="ratingOptions"
      placeholder="Any rating"
      class="w-full"
    />
  </div>
</template>
