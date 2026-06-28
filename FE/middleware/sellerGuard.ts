import { useAuthStore } from "~/stores/auth";

export default defineNuxtRouteMiddleware(async () => {
  const authStore = useAuthStore();

  if (import.meta.server) {
    return;
  }

  if (!authStore.user) {
    await authStore.fetchUser();
  }

  if (!authStore.user) {
    return navigateTo("/auth/signin");
  }

  if (authStore.user.role !== "SELLER") {
    return navigateTo("/");
  }
});