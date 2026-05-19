export type AdStatus = "DRAFT" | "ACTIVE" | "PAUSED" | "COMPLETED";
export type BidType = "CPC" | "CPM";

export interface Campaign {
  id: number;
  name: string;
  status: AdStatus;
  startDate: string | null;
  endDate: string | null;
}

export interface AdGroup {
  id: number;
  campaignId: number;
  name: string;
  bidType: BidType;
  bidAmount: number;
  status: AdStatus;
}

export interface Creative {
  id: number;
  adGroupId: number;
  title: string;
  description: string | null;
  imageUrl: string | null;
  clickUrl: string;
  status: AdStatus;
}
