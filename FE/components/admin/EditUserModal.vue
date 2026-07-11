<script setup lang="ts">
import type { User } from "~/types/user";
import { countryCodes } from "~/constants/countryCodeConstants";

const open = defineModel<boolean>("open", {
  default: false,
});

const props = defineProps<{
  user: User | null;
}>();

const emit = defineEmits<{
  updated: [user: User];
}>();

const api = useApi();
const toast = useToast();

const isSaving = ref(false);

const form = reactive({
  name: "",
  surname: "",
  email: "",
  countryCode: "+385",
  phoneNumber: "",
  role: "USER",
});

function splitPhoneNumber(value: string) {
  const code = countryCodes.find((item) => value.startsWith(item.value));

  if (!code) {
    return {
      countryCode: "+385",
      phoneNumber: value,
    };
  }

  return {
    countryCode: code.value,
    phoneNumber: value.replace(code.value, ""),
  };
}

watch(
  () => props.user,
  (user) => {
    if (!user) return;

    const phone = splitPhoneNumber(user.phoneNumber || "");

    form.name = user.name;
    form.surname = user.surname;
    form.email = user.email;
    form.countryCode = phone.countryCode;
    form.phoneNumber = phone.phoneNumber;
    form.role = user.role;
  },
  { immediate: true },
);

function closeModal() {
  open.value = false;
}

async function saveUserChanges() {
  if (!props.user) return;

  isSaving.value = true;

  try {
    const updatedUser = await api<User>(`/admin/users/${props.user.id}`, {
      method: "POST",
      body: {
        name: form.name,
        surname: form.surname,
        email: form.email,
        phoneNumber: `${form.countryCode}${form.phoneNumber}`,
        role: form.role,
      },
    });

    emit("updated", updatedUser);

    toast.add({
      title: "Saved",
      description: "User updated successfully.",
      color: "success",
    });

    closeModal();
  } catch (error) {
    console.error(error);

    toast.add({
      title: "Error",
      description: "Failed to update user.",
      color: "error",
    });
  } finally {
    isSaving.value = false;
  }
}
</script>

<template>
  <UModal
    v-model:open="open"
    title="Edit User"
    description="Update account information and role."
    :ui="{
      content:
        'w-[95vw] max-w-lg max-h-[90vh] overflow-hidden bg-white p-3 sm:p-4',
    }"
  >
    <template #content>
      <div class="max-h-[calc(90vh-2rem)] overflow-y-auto">
        <div class="mb-6 text-center">
          <h2 class="text-xl font-semibold">Edit User</h2>

          <p class="mt-1 text-sm text-slate-500">
            Update account information and role.
          </p>
        </div>

        <div class="mx-auto flex justify-center">
          <UCard class="w-full max-w-md">
            <template #header>
              <h3 class="text-center font-semibold">User Information</h3>
            </template>

            <div class="mx-auto flex max-w-sm flex-col items-center space-y-4">
              <UFormField label="First Name" class="w-full">
                <UInput
                  v-model="form.name"
                  placeholder="Enter first name"
                  class="w-full"
                />
              </UFormField>

              <UFormField label="Last Name" class="w-full">
                <UInput
                  v-model="form.surname"
                  placeholder="Enter last name"
                  class="w-full"
                />
              </UFormField>

              <UFormField label="Email Address" class="w-full">
                <UInput
                  v-model="form.email"
                  placeholder="Enter email"
                  class="w-full"
                />
              </UFormField>

              <UFormField label="Phone Number" class="w-full">
                <div class="flex w-full gap-2">
                  <USelectMenu
                    v-model="form.countryCode"
                    :items="countryCodes"
                    value-key="value"
                    label-key="label"
                    class="w-28"
                  />

                  <UInput
                    v-model="form.phoneNumber"
                    placeholder="Enter phone number"
                    class="flex-1"
                  />
                </div>
              </UFormField>

              <UFormField label="Role" class="w-full">
                <USelect
                  v-model="form.role"
                  :items="['USER', 'SELLER', 'ADMIN']"
                  class="w-full"
                />
              </UFormField>

              <div
                class="grid w-full grid-cols-1 gap-2 pt-4 sm:flex sm:justify-center"
              >
                <UButton
                  label="Cancel"
                  variant="soft"
                  color="neutral"
                  block
                  :loading="isSaving"
                  :disabled="isSaving"
                  class="sm:w-auto"
                  @click="closeModal"
                />

                <UButton
                  label="Save Changes"
                  :loading="isSaving"
                  :disabled="isSaving"
                  block
                  class="bg-indigo-600 text-white hover:bg-indigo-700 sm:w-auto"
                  @click="saveUserChanges"
                />
              </div>
            </div>
          </UCard>
        </div>
      </div>
    </template>
  </UModal>
</template>
