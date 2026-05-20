import { Campaign, AdGroup, Creative } from "@/types/ad";

let campaignSeq = 1;
let adGroupSeq = 1;
let creativeSeq = 1;

export const campaigns: Map<number, Campaign> = new Map();
export const adGroups: Map<number, AdGroup> = new Map();
export const creatives: Map<number, Creative> = new Map();

export function nextCampaignId() {
  return campaignSeq++;
}
export function nextAdGroupId() {
  return adGroupSeq++;
}
export function nextCreativeId() {
  return creativeSeq++;
}
