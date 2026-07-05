export type PriceAdjustment = {
  startDate: string;
  endDate: string;
  percent: number;
};

export type ListingStatus = "PENDING" | "APPROVED" | "REJECTED" | "DELETED";

export type ListingUnit = {
  id?: number;
  type: string;
  label: string;
  quantity: number;
  availableQuantity?: number;
  maxGuests?: number;
  roomCount: number;
  pricePerNight: number;
};

export type ListingLocation = {
  location: string;
  latitude: number | null;
  longitude: number | null;
  city?: string;
};

export type ListingPrice = {
  lowestPrice: number;
  highestPrice: number;
};

export type ListingReviewSummary = {
  rating: number;
  averageRating: number;
  reviewCount: number;
};

export type FavoriteListing = ListingLocation &
  ListingPrice & {
    id: number;

    title: string;
    description: string;

    rating: number;

    images: string[];
  };

export type Listing = ListingLocation &
  ListingPrice &
  ListingReviewSummary & {
    id: number;

    title: string;
    description: string;

    images: string[];
    amenities: string[];

    units: ListingUnit[];
    priceAdjustments: PriceAdjustment[];

    availableFrom: string;

    businessName: string;

    sellerId: number;
    sellerName: string;
    sellerSurname: string;
    sellerEmail: string;
    sellerPhoneNumber: string | null;
    sellerProfileImage: string | null;

    status: ListingStatus;
    createdAt: string;
  };
