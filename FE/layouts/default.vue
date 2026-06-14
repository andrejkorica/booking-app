<script setup lang="ts">
import Navbar from '~/components/navbar.vue';
</script>

<template>
  <div>
    <Navbar />

    <main class="min-h-screen bg-slate-50">
      <slot />
    </main>
  </div>
</template>