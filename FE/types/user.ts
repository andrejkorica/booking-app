
export type UserRole = "USER" | "SELLER" | "ADMIN";

export type User = {
  id: number
  name: string
  surname: string
  email: string
  phoneNumber: string
  role: UserRole
}
