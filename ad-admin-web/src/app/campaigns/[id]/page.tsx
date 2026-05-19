import Link from "next/link";
import { Campaign, AdGroup } from "@/types/ad";
import { StatusBadge } from "@/components/StatusBadge";
import { StatusActions } from "@/components/StatusActions";
import { DeleteCampaignButton } from "./DeleteCampaignButton";
import { buttonVariants } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

async function getCampaign(id: string): Promise<Campaign> {
  const res = await fetch(`http://localhost:3001/api/campaigns/${id}`, {
    cache: "no-store",
  });
  if (!res.ok) throw new Error("Campaign not found");
  return res.json();
}

async function getAdGroups(campaignId: string): Promise<AdGroup[]> {
  const res = await fetch(
    `http://localhost:3001/api/campaigns/${campaignId}/ad-groups`,
    { cache: "no-store" },
  );
  return res.json();
}

export default async function CampaignDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const [campaign, adGroups] = await Promise.all([
    getCampaign(id),
    getAdGroups(id),
  ]);

  return (
    <div>
      <div className="flex items-center gap-3 mb-1">
        <h1 className="text-2xl font-bold">{campaign.name}</h1>
        <StatusBadge status={campaign.status} />
      </div>
      <p className="text-sm text-muted-foreground mb-4">
        {campaign.startDate ?? "시작일 미정"} ~{" "}
        {campaign.endDate ?? "종료일 미정"}
      </p>
      <div className="flex gap-2 mb-6">
        <StatusActions
          status={campaign.status}
          apiUrl={`/api/campaigns/${id}/status`}
        />
        <Link
          href={`/campaigns/${id}/edit`}
          className={buttonVariants({ variant: "outline", size: "sm" })}
        >
          수정
        </Link>
        <DeleteCampaignButton id={id} />
      </div>

      <Separator className="mb-6" />

      <div className="flex justify-between items-center mb-4">
        <h2 className="text-lg font-semibold">광고그룹</h2>
        <Link
          href={`/campaigns/${id}/ad-groups/new`}
          className={buttonVariants({ size: "sm" })}
        >
          광고그룹 추가
        </Link>
      </div>

      {adGroups.length === 0 ? (
        <div className="text-center py-12 text-muted-foreground">
          등록된 광고그룹이 없습니다.
        </div>
      ) : (
        <div className="border rounded-lg">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-16">ID</TableHead>
                <TableHead>광고그룹명</TableHead>
                <TableHead className="w-20">입찰</TableHead>
                <TableHead className="w-28">입찰가</TableHead>
                <TableHead className="w-24">상태</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {adGroups.map((ag) => (
                <TableRow key={ag.id}>
                  <TableCell className="text-muted-foreground">
                    {ag.id}
                  </TableCell>
                  <TableCell>
                    <Link
                      href={`/campaigns/${id}/ad-groups/${ag.id}`}
                      className="text-primary hover:underline font-medium"
                    >
                      {ag.name}
                    </Link>
                  </TableCell>
                  <TableCell>{ag.bidType}</TableCell>
                  <TableCell>{ag.bidAmount.toLocaleString()}원</TableCell>
                  <TableCell>
                    <StatusBadge status={ag.status} />
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      )}

      <div className="mt-6">
        <Link
          href="/campaigns"
          className="text-sm text-muted-foreground hover:underline"
        >
          ← 캠페인 목록
        </Link>
      </div>
    </div>
  );
}
