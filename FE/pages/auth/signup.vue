<script setup lang="ts">
import { reactive } from "vue";
import type { FormSubmitEvent } from "#ui/types";
import { useAuthStore } from "~/stores/auth";
import { countryCodes } from "~/constants/CountryCodeConstants";

const config = useRuntimeConfig();
const toast = useToast();
const auth = useAuthStore();

definePageMeta({
  layout: "auth",
  middleware: "guest-guard"
});

const state = reactive({
  name: "",
  surname: "",
  countryCode: "+385",
  phoneNumber: "",
  email: "",
  password: "",
  passwordConfirm: "",
});

function isOneWordWithCapitalFirstLetter(value: string) {
  return /^[A-ZČĆŽŠĐ][a-zA-ZČĆŽŠĐčćžšđ]*$/.test(value.trim());
}

function isValidEmail(value: string) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value.trim());
}

function isValidPhone(value: string) {
  return /^\d{6,15}$/.test(value.trim());
}

async function onSubmit(event: FormSubmitEvent<any>) {
  const name = event.data.name.trim();
  const surname = event.data.surname.trim();
  const email = event.data.email.trim();
  const phoneNumber = event.data.phoneNumber.trim();

  if (!isOneWordWithCapitalFirstLetter(name)) {
    toast.add({
      title: "Invalid name",
      description: "Name must be one word and start with an uppercase letter.",
      color: "error",
    });
    return;
  }

  if (!isOneWordWithCapitalFirstLetter(surname)) {
    toast.add({
      title: "Invalid surname",
      description:
        "Surname must be one word and start with an uppercase letter.",
      color: "error",
    });
    return;
  }

  if (!isValidEmail(email)) {
    toast.add({
      title: "Invalid email",
      description: "Please enter a valid email address.",
      color: "error",
    });
    return;
  }

  if (!isValidPhone(phoneNumber)) {
    toast.add({
      title: "Invalid phone number",
      description:
        "Phone number should contain only numbers and be 6-15 digits long.",
      color: "error",
    });
    return;
  }

  if (event.data.password !== event.data.passwordConfirm) {
    toast.add({
      title: "Error",
      description: "Passwords do not match",
      color: "error",
    });
    return;
  }

  try {
    await $fetch(`${config.public.apiBase}/users/register`, {
      method: "POST",
      body: {
        name,
        surname,
        phoneNumber: `${state.countryCode}${phoneNumber}`,
        email,
        password: event.data.password,
      },
      credentials: "include",
    });

    await auth.fetchUser();

    toast.add({
      title: "Success",
      description: "Account created successfully!",
      color: "success",
    });

    await navigateTo("/");
  } catch (err: any) {
    toast.add({
      title: "Registration failed",
      description: err?.data?.message || "Unable to create account",
      color: "error",
    });
  }
}
</script>

<template>
  <div
    class="flex min-h-screen items-center justify-center bg-gradient-to-br from-indigo-50 via-white to-slate-100 py-12"
  >
    <UCard class="w-full max-w-md border-0 bg-white shadow-2xl">
      <div class="space-y-6">
        <div class="text-center">
          <div
            class="mx-auto mb-4 flex h-16 w-16 items-center justify-center rounded-lg bg-indigo-100"
          >
            <UIcon name="i-lucide-user-plus" class="h-8 w-8 text-indigo-600" />
          </div>

          <h1 class="text-2xl font-bold text-slate-900">Create an account</h1>
          <p class="text-sm text-slate-500">Enter your details to sign up.</p>
        </div>

        <UForm :state="state" class="space-y-4" @submit="onSubmit">
          <div class="grid grid-cols-2 gap-4">
            <UFormField name="name">
              <template #label>
                <span class="sr-only">Name</span>
              </template>

              <UInput
                v-model="state.name"
                placeholder="Name"
                size="lg"
                class="w-full"
              />
            </UFormField>

            <UFormField name="surname">
              <template #label>
                <span class="sr-only">Last name</span>
              </template>

              <UInput
                v-model="state.surname"
                placeholder="Last name"
                size="lg"
                class="w-full"
              />
            </UFormField>
          </div>

          <UFormField name="phoneNumber">
            <template #label>
              <span class="sr-only">Phone Number</span>
            </template>

            <div class="flex gap-2">
              <USelectMenu
                v-model="state.countryCode"
                :items="countryCodes"
                value-key="value"
                label-key="label"
                size="lg"
                class="w-36"
              />

              <UInput
                v-model="state.phoneNumber"
                placeholder="Phone Number"
                size="lg"
                class="flex-1"
              />
            </div>
          </UFormField>

          <UFormField name="email">
            <template #label>
              <span class="sr-only">Email</span>
            </template>

            <UInput
              v-model="state.email"
              placeholder="Enter your email..."
              size="lg"
              class="w-full"
            />
          </UFormField>

          <UFormField name="password">
            <template #label>
              <span class="sr-only">Password</span>
            </template>

            <UInput
              v-model="state.password"
              type="password"
              placeholder="Password"
              size="lg"
              class="w-full"
            />
          </UFormField>

          <UFormField name="passwordConfirm">
            <template #label>
              <span class="sr-only">Confirm Password</span>
            </template>

            <UInput
              v-model="state.passwordConfirm"
              type="password"
              placeholder="Confirm Password"
              size="lg"
              class="w-full"
            />
          </UFormField>

          <UButton
            type="submit"
            label="Sign Up"
            size="lg"
            block
            class="mt-6 bg-indigo-600 font-bold text-white hover:bg-indigo-700"
          />
        </UForm>

        <p class="text-center text-sm text-slate-500">
          Already have an account?

          <ULink
            to="/auth/signin"
            class="font-semibold text-indigo-600 hover:text-indigo-700"
          >
            Sign in
          </ULink>
        </p>
        <p class="text-sm text-center text-slate-500">
          Want to go back?
          <ULink
            to="/"
            class="font-semibold text-indigo-600 hover:text-indigo-700"
          >
            Home page
          </ULink>
        </p>
      </div>
    </UCard>
  </div>
</template>
