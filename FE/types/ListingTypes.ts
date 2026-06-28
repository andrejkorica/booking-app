export type ListingUnit = {
  id?: number;
  type: string;
  label: string;
  quantity: number;
  availableQuantity?: number;
  maxGuests?: number;
  pricePerNight: number;
  roomCount: number;
};

export type Listing = {
  id: number;
  title: string;
  location: string;
  latitude: number | null;
  longitude: number | null;
  description: string;
  rating: number;
  lowestPrice: number;
  highestPrice: number;
  averageRating: number;
  reviewCount: number;
  images: string[];
  amenities: string[];
  availableFrom: string;
  units: ListingUnit[];
  priceAdjustments: PriceAdjustment[];
  status: string;
  sellerEmail: string;
  createdAt: string;
};

export type PriceAdjustment = {
  startDate: string
  endDate: string
  percent: number
}
