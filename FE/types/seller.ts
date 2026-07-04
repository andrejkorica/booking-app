export type SellerRequestStatus =
  | "PENDING"
  | "APPROVED"
  | "REJECTED"
  | "UPDATE";

export type SellerRequest = {
  id: number;
  requestText: string;
  status: SellerRequestStatus;
  createdAt: string;
  userName: string;
  userSurname: string;
  userEmail: string;
  businessName: string;
  oib: string;
  iban: string;
  billingAddress: string;
};

export type SellerDashboardStats = {
  totalEarnings: number;
  totalListings: number;
  activeBookings: number;
};
