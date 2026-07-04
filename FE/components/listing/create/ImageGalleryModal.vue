<script setup lang="ts">
import type { ListingImage } from "~/types/image.js";

defineProps<{
  images: ListingImage[];
}>();

const open = defineModel<boolean>("open", { default: false });

function closeModal() {
  open.value = false;
}
</script>

<template>
  <UModal
    v-model:open="open"
    title="Listing images"
    description="Image gallery for this listing"
    :ui="{
      content: 'w-[95vw] max-w-5xl max-h-[90vh] overflow-hidden bg-white p-0',
    }"
  >
    <template #content>
      <div class="flex max-h-[90vh] flex-col">
        <div
          class="flex items-center justify-between border-b border-slate-200 px-4 py-3"
        >
          <div>
            <h2 class="text-base font-semibold text-slate-900 sm:text-lg">
              Listing images
            </h2>
            <p class="text-xs text-slate-500 sm:text-sm">
              Image gallery for this listing
            </p>
          </div>

          <UButton
            icon="i-lucide-x"
            color="neutral"
            variant="ghost"
            size="sm"
            @click="closeModal"
          />
        </div>

        <div class="p-3 sm:p-8 lg:p-16">
          <UCarousel
            v-slot="{ item }"
            :items="images"
            arrows
            dots
            :prev="{
              icon: 'i-ph-arrow-left',
              color: 'primary',
              variant: 'solid',
              class: 'bg-white border-white text-slate-900 shadow-lg',
            }"
            :next="{
              icon: 'i-ph-arrow-right',
              color: 'primary',
              variant: 'solid',
              class: 'bg-white border-white text-slate-900 shadow-lg',
            }"
            :ui="{
              item: 'h-[55vh] sm:h-[70vh] w-full',
              dot: 'bg-primary',
            }"
          >
            <img
              :src="item.previewUrl"
              class="h-full w-full rounded-xl object-cover"
              loading="lazy"
              alt="Listing image"
            />
          </UCarousel>
        </div>
      </div>
    </template>
  </UModal>
</template>
