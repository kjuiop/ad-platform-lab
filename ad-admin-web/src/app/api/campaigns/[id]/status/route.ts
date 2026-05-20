import { NextRequest, NextResponse } from "next/server";
import { campaigns } from "@/lib/mock-store";
import { AdStatus } from "@/types/ad";

const validTransitions: Record<AdStatus, AdStatus[]> = {
  DRAFT: ["ACTIVE"],
  ACTIVE: ["PAUSED", "COMPLETED"],
  PAUSED: ["ACTIVE", "COMPLETED"],
  COMPLETED: [],
};

export async function PATCH(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaign = campaigns.get(Number(id));
  if (!campaign) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  const { status } = await request.json();
  if (!validTransitions[campaign.status]?.includes(status)) {
    return NextResponse.json(
      {
        error: `${campaign.status}에서 ${status}로 변경할 수 없습니다.`,
      },
      { status: 400 },
    );
  }
  campaign.status = status;
  return NextResponse.json(campaign);
}
