export type ReviewVote = "UP" | "DOWN" | null;

export type ListingReview = {
  id: number;
  userName: string;
  rating: number;
  comment: string;
  createdAt: string;
  upvotes: number;
  downvotes: number;
  currentUserVote: ReviewVote;
};