import { NextRequest, NextResponse } from "next/server";
import { campaigns, nextCampaignId } from "@/lib/mock-store";
import { Campaign } from "@/types/ad";

export async function GET() {
  const list = Array.from(campaigns.values());
  return NextResponse.json(list);
}

export async function POST(request: NextRequest) {
  const body = await request.json();
  if (!body.name) {
    return NextResponse.json(
      { error: "name은 필수입니다." },
      { status: 400 },
    );
  }
  const id = nextCampaignId();
  const campaign: Campaign = {
    id,
    name: body.name,
    status: "DRAFT",
    startDate: body.startDate ?? null,
    endDate: body.endDate ?? null,
  };
  campaigns.set(id, campaign);
  return NextResponse.json(campaign, { status: 201 });
}
