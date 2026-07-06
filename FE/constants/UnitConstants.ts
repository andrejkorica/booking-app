import type { UnitTemplate } from "~/types/listing"

export const unitTemplate: UnitTemplate[] = [
  { label: 'Single room', type: 'single_room', maxGuests: 1, roomCount: 1 },
  { label: 'Double room', type: 'double_room', maxGuests: 2, roomCount: 1 },
  { label: 'Twin room', type: 'twin_room', maxGuests: 2, roomCount: 1 },
  { label: 'Queen room', type: 'queen_room', maxGuests: 2, roomCount: 1 },
  { label: 'King room', type: 'king_room', maxGuests: 2, roomCount: 1 },
  { label: 'Studio apartment', type: 'studio_apartment', maxGuests: 2, roomCount: 1 },
  { label: 'One-bedroom apartment', type: 'one_bedroom_apartment', maxGuests: 4, roomCount: 1 },
  { label: 'Two-bedroom apartment', type: 'two_bedroom_apartment', maxGuests: 6, roomCount: 2 },
  { label: 'Three-bedroom apartment', type: 'three_bedroom_apartment', maxGuests: 8, roomCount: 3 },
  { label: 'Family apartment', type: 'family_apartment', maxGuests: 6, roomCount: 2 },
  { label: 'Penthouse apartment', type: 'penthouse_apartment', maxGuests: 8, roomCount: 3 }
]