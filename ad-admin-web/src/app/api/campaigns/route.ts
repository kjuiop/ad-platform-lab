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
  const isoDateRegex = /^\d{4}-\d{2}-\d{2}$/;
  for (const field of ["startDate", "endDate"] as const) {
    if (body[field] != null) {
      if (!isoDateRegex.test(body[field]) || isNaN(Date.parse(body[field]))) {
        return NextResponse.json(
          { error: `${field}은(는) YYYY-MM-DD 형식이어야 합니다.` },
          { status: 400 },
        );
      }
    }
  }
  if (body.startDate && body.endDate && body.startDate > body.endDate) {
    return NextResponse.json(
      { error: "startDate는 endDate보다 이전이어야 합니다." },
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
