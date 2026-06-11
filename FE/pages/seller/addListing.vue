<template>
  <div class="bg-white text-slate-900 min-h-screen">
    <UContainer class="py-12">
      <header class="mb-10">
        <h1 class="text-4xl md:text-5xl font-bold tracking-tight text-slate-900">
          Add New Property
        </h1>
        <p class="mt-2 text-lg text-slate-500">
          Fill out the form to add a new property to the listing.
        </p>
      </header>

      <div class="max-w-5xl mx-auto">
        <form @submit.prevent="handleSubmit" class="space-y-8">
          
          <div class="grid grid-cols-2 md:grid-cols-2 gap-8">
            <UFormGroup label="Property Title" class="w-full" :ui="{ label: { base: 'text-slate-700 font-bold' } }">
              <UInput 
                v-model="listing.title"
                placeholder="e.g. Hotel Ambasador" 
                size="xl" 
                icon="i-heroicons-building-office-2"
                required
                class="w-full"
              />
            </UFormGroup>

            <UFormGroup label="Amenities" :ui="{ label: { base: 'text-slate-700 font-bold' } }">
              <UInputMenu
                v-model="listing.amenities"
                :items="availableAmenities"
                multiple
                placeholder="Select amenities..."
                icon="i-heroicons-queue-list"
                size="xl"
                class="w-full"
              />
            </UFormGroup>
          </div>

          <UFormGroup label="Property Description" :ui="{ label: { base: 'text-slate-700 font-bold' } }">
            <UTextarea 
              v-model="listing.description"
              placeholder="Describe the property, its advantages, and amenities..." 
              :rows="8"
              class="w-full"
              width="100%"
              size="xl"
              required
            />
          </UFormGroup>

          <div class="space-y-8 pt-4">
            <div>
              <label class="block text-sm font-bold text-slate-700 mb-2">Map Location</label>
              <div class="h-96 w-full bg-slate-100 border-2 border-dashed border-slate-300 rounded-xl flex items-center justify-center">
                <div class="text-center">
                  <UIcon name="i-heroicons-map" class="w-12 h-12 text-slate-400 mx-auto" />
                  <p class="mt-2 text-slate-500">Map Placeholder (e.g. Leaflet.js)</p>
                </div>
              </div>
            </div>

            <UCard class="bg-white shadow-lg border border-slate-200">
               <div class="text-center space-y-4">
                 <p class="text-lg text-slate-500">Price per night</p>
                 <div class="flex items-center justify-center">
                   <span class="text-4xl font-bold text-slate-400 mr-2">€</span>
                   <UInput 
                     v-model.number="listing.pricePerNight"
                     type="number"
                     placeholder="0"
                     :ui="{
                       base: 'bg-transparent border-none focus:ring-0 p-0 text-center text-4xl font-bold',
                     }"
                     required
                   />
                 </div>
               </div>
            </UCard>
            
            <UButton 
              type="submit" 
              label="Save Property" 
              size="xl" 
              block
              class="bg-indigo-600 hover:bg-indigo-700 text-white font-bold"
            />
          </div>

        </form>
      </div>
    </UContainer>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const availableAmenities = [
  'Rooftop pool',
  'Spa & wellness center',
  'Free WiFi',
  'Restaurant & bar',
  'Fitness center',
  'Sea view',
  'Air conditioning',
  'Private bathroom',
  'Pet friendly',
  'Parking',
  'Safe',
  'Breakfast included'
];

const listing = ref({
  title: '',
  description: '',
  amenities: [] as string[],
  location: null,
  pricePerNight: null as number | null,
});

const handleSubmit = () => {
  console.log('Form Submitted. Listing Data:', listing.value);
};

useHead({
  title: 'Add New Property | Admin'
});
</script>

<style scoped>
input[type='number']::-webkit-inner-spin-button,
input[type='number']::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
input[type='number'] {
  -moz-appearance: textfield;
}
</style>