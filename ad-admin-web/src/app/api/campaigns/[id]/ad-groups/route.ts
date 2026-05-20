import { NextRequest, NextResponse } from "next/server";
import {
  campaigns,
  adGroups,
  nextAdGroupId,
} from "@/lib/mock-store";
import { AdGroup } from "@/types/ad";

export async function GET(
  _request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaignId = Number(id);
  if (!campaigns.has(campaignId)) {
    return NextResponse.json({ error: "Campaign not found" }, { status: 404 });
  }
  const list = Array.from(adGroups.values()).filter(
    (ag) => ag.campaignId === campaignId,
  );
  return NextResponse.json(list);
}

export async function POST(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaignId = Number(id);
  if (!campaigns.has(campaignId)) {
    return NextResponse.json({ error: "Campaign not found" }, { status: 404 });
  }
  const body = await request.json();
  const validBidTypes = ["CPC", "CPM"];
  if (
    !body.name ||
    !validBidTypes.includes(body.bidType) ||
    !Number.isFinite(body.bidAmount) ||
    body.bidAmount <= 0
  ) {
    return NextResponse.json(
      { error: "name은 필수, bidType은 CPC/CPM, bidAmount는 0보다 큰 숫자여야 합니다." },
      { status: 400 },
    );
  }
  const agId = nextAdGroupId();
  const adGroup: AdGroup = {
    id: agId,
    campaignId,
    name: body.name,
    bidType: body.bidType,
    bidAmount: body.bidAmount,
    status: "DRAFT",
  };
  adGroups.set(agId, adGroup);
  return NextResponse.json(adGroup, { status: 201 });
}
