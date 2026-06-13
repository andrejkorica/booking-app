<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'

const authStore = useAuthStore()
const config = useRuntimeConfig()
const toast = useToast()

const isSending = ref(false)

const form = reactive({
  businessName: '',
  oib: '',
  iban: '',
  address: '',
  requestText: ''
})

const isSeller = computed(() => authStore.user?.role === 'SELLER')

async function sendSellerRequest() {
  isSending.value = true

  try {
    await $fetch(`${config.public.apiBase}/seller-requests`, {
      method: 'POST',
      credentials: 'include',
      body: form
    })

    toast.add({
      title: 'Request sent',
      description: 'Your seller request has been sent for review.',
      color: 'success'
    })
  } catch (error) {
    console.error(error)

    toast.add({
      title: 'Error',
      description: 'Failed to send seller request.',
      color: 'error'
    })
  } finally {
    isSending.value = false
  }
}
</script>

<template>
  <div class="space-y-6">
    <template v-if="isSeller">
      <h2 class="text-xl font-semibold">
        Seller Info
      </h2>

      <p class="text-sm text-slate-600">
        You are already registered as a seller.
      </p>
    </template>

    <template v-else>
      <h2 class="text-xl font-semibold">
        Become a seller
      </h2>

      <p class="text-sm text-slate-600">
        Fill in your billing information and send a request to become a seller.
        An admin needs to approve your request first.
      </p>

      <div class="max-w-md space-y-4">
        <UInput
          v-model="form.businessName"
          placeholder="Business name"
        />

        <UInput
          v-model="form.oib"
          placeholder="OIB"
        />

        <UInput
          v-model="form.iban"
          placeholder="IBAN"
        />

        <UInput
          v-model="form.address"
          placeholder="Billing address"
        />

        <UTextarea
          v-model="form.requestText"
          placeholder="Tell us why you want to become a seller"
          :rows="4"
        />

        <UButton
          label="Send request"
          :loading="isSending"
          class="bg-indigo-600 text-white hover:bg-indigo-700"
          @click="sendSellerRequest"
        />
      </div>
    </template>
  </div>
</template>