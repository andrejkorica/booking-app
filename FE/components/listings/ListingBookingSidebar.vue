<script setup lang="ts">
import ListingSellerInfoModal from "~/components/listings/ListingSellerInfoModal.vue";

const props =defineProps<{
  priceLabel: string;
  canBook: boolean;
  bookTo?: string;
  disabledMessage?: string;
  isFavorited: boolean;
  isTogglingFavorite: boolean;
  listingData: {
    sellerId: number;
    businessName: string;
    sellerName: string;
    sellerSurname: string;
    sellerProfileImage: string | null;
    sellerEmail: string;
    sellerPhoneNumber: string | null;
  };
}>();

const avatarSrc = computed(() => {
  return props.listingData.sellerProfileImage || "/images/default-avatar.png";
});

const isSellerModalOpen = ref(false);

const emit = defineEmits<{
  toggleFavorite: [];
}>();
</script>

<template>
  <div class="sticky top-6 space-y-4">
    <UCard class="border border-slate-200 bg-white shadow-lg">
      <div class="flex items-center gap-4">
        <UAvatar
          :src="avatarSrc"
          :alt="listingData.sellerName"
          size="lg"
        />

        <div class="min-w-0">
          <p class="text-sm text-slate-500">Hosted by</p>

          <button
            type="button"
            class="truncate text-left font-semibold text-slate-900 underline underline-offset-4 transition hover:text-indigo-600"
            @click="isSellerModalOpen = true"
          >
            {{ listingData.sellerName }} {{ listingData.sellerSurname }}
          </button>
        </div>
      </div>
    </UCard>

    <UCard class="border border-slate-200 bg-white shadow-lg">
      <div class="space-y-4 text-center">
        <p class="text-lg text-slate-500">Price per night</p>

        <p class="text-4xl font-bold">
          {{ priceLabel }}
        </p>

        <UButton
          label="Book now"
          size="xl"
          block
          class="bg-indigo-600 font-bold text-white hover:bg-indigo-700"
          :disabled="!canBook"
          :to="canBook ? bookTo : undefined"
        />

        <p v-if="disabledMessage" class="text-sm text-slate-500">
          {{ disabledMessage }}
        </p>
      </div>
    </UCard>

    <UCard class="border border-slate-200 bg-white shadow-lg">
      <div class="space-y-4 text-center">
        <p class="text-lg font-semibold text-slate-900">Save this listing</p>

        <UButton
          :label="isFavorited ? 'Added to favorites' : 'Add to favorites'"
          size="lg"
          block
          variant="soft"
          color="neutral"
          :icon="isFavorited ? 'i-heroicons-heart-solid' : 'i-heroicons-heart'"
          :class="isFavorited ? 'text-red-500' : ''"
          :loading="isTogglingFavorite"
          @click="emit('toggleFavorite')"
        />
      </div>
    </UCard>

    <ListingSellerInfoModal
      v-model:open="isSellerModalOpen"
      :seller="{
        id: listingData.sellerId,
        businessName: listingData.businessName,
        name: listingData.sellerName,
        surname: listingData.sellerSurname,
        email: listingData.sellerEmail,
        phoneNumber: listingData.sellerPhoneNumber,
        profileImageUrl: listingData.sellerProfileImage,
      }"
    />
  </div>
</template>
