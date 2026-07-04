<script setup lang="ts">
import { useAuthStore } from "~/stores/auth";

const authStore = useAuthStore();
const config = useRuntimeConfig();
const toast = useToast();

const isSending = ref(false);

const form = reactive({
  businessName: "",
  oib: "",
  iban: "",
  address: "",
  requestText: "",
});

const isSeller = computed(() => authStore.user?.role === "SELLER");
const isAdmin = computed(() => authStore.user?.role === "ADMIN");

async function sendSellerRequest() {
  if (isAdmin.value) {
    return;
  }

  isSending.value = true;

  try {
    await $fetch(`${config.public.apiBase}/seller-requests`, {
      method: "POST",
      credentials: "include",
      body: form,
    });

    toast.add({
      title: "Request sent",
      description: "Your seller request has been sent for review.",
      color: "success",
    });
  } catch (error) {
    console.error(error);

    toast.add({
      title: "Error",
      description: "Failed to send seller request.",
      color: "error",
    });
  } finally {
    isSending.value = false;
  }
}

async function fetchSellerInfo() {
  if (!isSeller.value) {
    return;
  }

  try {
    const sellerInfo = await $fetch<{
      businessName: string;
      oib: string;
      iban: string;
      billingAddress: string;
    }>(`${config.public.apiBase}/seller/me`, {
      credentials: "include",
    });

    form.businessName = sellerInfo.businessName ?? "";
    form.oib = sellerInfo.oib ?? "";
    form.iban = sellerInfo.iban ?? "";
    form.address = sellerInfo.billingAddress ?? "";
  } catch (error) {
    console.error(error);
  }
}

onMounted(async () => {
  if (isSeller.value) {
    await fetchSellerInfo();
  }
});
</script>
<template>
  <div class="space-y-6">
    <template v-if="isAdmin">
      <div class="mx-auto flex justify-center">
        <UCard class="w-full max-w-md">
          <div class="flex flex-col items-center py-8 text-center">
            <UIcon name="i-lucide-lock" class="mb-4 size-12 text-slate-400" />

            <h2 class="text-xl font-semibold">Seller Information Locked</h2>

            <p class="mt-2 text-sm text-slate-600">
              Administrator accounts cannot become sellers or submit seller
              requests.
            </p>
          </div>
        </UCard>
      </div>
    </template>

    <template v-else>
      <div class="text-center">
        <h2 class="text-xl font-semibold">
          {{ isSeller ? "Seller Info" : "Become a Seller" }}
        </h2>

        <p class="mx-auto mt-1 max-w-md text-sm text-slate-600">
          {{
            isSeller
              ? "Update your seller billing information."
              : "Fill in your billing information and send a request to become a seller. An admin needs to approve your request first."
          }}
        </p>
      </div>

      <div class="mx-auto flex justify-center">
        <UCard class="w-full max-w-md">
          <template #header>
            <h3 class="text-center font-semibold">
              {{ isSeller ? "Update Seller Info" : "Seller Request" }}
            </h3>
          </template>

          <div class="mx-auto flex max-w-sm flex-col items-center space-y-4">
            <UFormField label="Business Name" class="w-full">
              <UInput
                v-model="form.businessName"
                placeholder="Enter your business name"
                class="w-full"
              />
            </UFormField>

            <UFormField label="OIB" class="w-full">
              <UInput
                v-model="form.oib"
                placeholder="Enter your OIB"
                class="w-full"
              />
            </UFormField>

            <UFormField label="IBAN" class="w-full">
              <UInput
                v-model="form.iban"
                placeholder="Enter your IBAN"
                class="w-full"
              />
            </UFormField>

            <UFormField label="Billing Address" class="w-full">
              <UInput
                v-model="form.address"
                placeholder="Enter your billing address"
                class="w-full"
              />
            </UFormField>

            <UFormField label="Request Message" class="w-full">
              <UTextarea
                v-model="form.requestText"
                :placeholder="
                  isSeller
                    ? 'Tell the admin why you want to update'
                    : 'Tell us why you want to become a seller'
                "
                :rows="4"
                class="w-full"
              />
            </UFormField>

            <div class="flex w-full justify-center pt-4">
              <UButton
                :label="isSeller ? 'Send Update Request' : 'Send Request'"
                :loading="isSending"
                class="bg-indigo-600 text-white hover:bg-indigo-700"
                @click="sendSellerRequest"
              />
            </div>
          </div>
        </UCard>
      </div>
    </template>
  </div>
</template>
