<script setup lang="ts">
import type { TabsItem } from "@nuxt/ui";

const isOpen = defineModel<boolean>("open", {
  default: false,
});

const selectedTab = defineModel<string>("tab", {
  default: "profile",
});

const items: TabsItem[] = [
  { label: "Profile", value: "profile", icon: "i-lucide-user" },
  { label: "Seller Info", value: "seller", icon: "i-lucide-store" },
];

function closeModal() {
  isOpen.value = false;
}
</script>

<template>
  <UModal
    v-model:open="isOpen"
    :ui="{
      content:
        'w-[95vw] max-w-sm sm:max-w-xl lg:max-w-3xl max-h-[90vh] p-3 sm:p-4 overflow-hidden',
    }"
    title="Account settings"
    description="Manage your profile, seller information, and booking history."
  >
    <template #content>
      <div class="flex max-h-[calc(90vh-2rem)] flex-col space-y-4">
        <UTabs
          v-model="selectedTab"
          :items="items"
          variant="link"
          class="w-full shrink-0"
          :ui="{
            list: 'flex w-full',
            trigger: 'flex-1 flex justify-center',
          }"
        />

        <div class="min-h-[220px] overflow-y-auto p-2 sm:min-h-[300px] sm:pr-3">
          <OptionsProfileTab v-if="selectedTab === 'profile'" />
          <OptionsSellerTab v-else-if="selectedTab === 'seller'" />
        </div>

        <!-- Mobile only -->
        <div class="border-t border-slate-200 pt-4 sm:hidden">
          <UButton
            label="Close"
            color="neutral"
            variant="outline"
            block
            @click="closeModal"
          />
        </div>
      </div>
    </template>
  </UModal>
</template>
