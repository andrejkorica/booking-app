<script setup lang="ts">
import { DialogTitle, DialogDescription, VisuallyHidden } from "reka-ui";


const props = defineProps<{
  open: boolean;
  seller: {
    id: number;
    name: string;
    surname: string;
    email: string;
    phoneNumber?: string | null;
    profileImageUrl?: string | null;
  };
}>();

const avatarSrc = computed(() => {
  return props.seller.profileImageUrl || "/images/default-avatar.png";
});

const emit = defineEmits<{
  "update:open": [value: boolean];
}>();
</script>

<template>
  <UModal :open="open" @update:open="emit('update:open', $event)">
    <template #content>
      <VisuallyHidden>
        <DialogTitle>Seller information</DialogTitle>
        <DialogDescription>
          View the seller's contact information and other listings
        </DialogDescription>
      </VisuallyHidden>

      <UCard class="border-0 shadow-none">
        <div class="space-y-6">
          <div class="flex items-center gap-5">
            <UAvatar :src="avatarSrc" size="3xl" />

            <div>
              <h2 class="text-2xl font-bold text-slate-900">
                {{ seller.name }} {{ seller.surname }}
              </h2>

              <p class="text-sm text-slate-500">Property host</p>
            </div>
          </div>

          <div class="space-y-3">
            <div
              class="flex items-center gap-4 rounded-lg border border-slate-200 p-4"
            >
              <UIcon
                name="i-heroicons-envelope"
                class="h-5 w-5 text-indigo-600"
              />

              <div>
                <p class="text-xs uppercase tracking-wide text-slate-500">
                  Email
                </p>

                <p class="font-medium">
                  {{ seller.email }}
                </p>
              </div>
            </div>

            <div
              v-if="seller.phoneNumber"
              class="flex items-center gap-4 rounded-lg border border-slate-200 p-4"
            >
              <UIcon name="i-heroicons-phone" class="h-5 w-5 text-indigo-600" />

              <div>
                <p class="text-xs uppercase tracking-wide text-slate-500">
                  Phone
                </p>

                <p class="font-medium">
                  {{ seller.phoneNumber }}
                </p>
              </div>
            </div>
          </div>

          <UButton
            label="View other listings"
            icon="i-heroicons-building-office-2"
            block
            color="neutral"
            variant="soft"
            class="transition hover:bg-indigo-100 hover:text-indigo-700"
            :to="`/listings?sellerId=${seller.id}`"
          />
        </div>
      </UCard>
    </template>
  </UModal>
</template>
