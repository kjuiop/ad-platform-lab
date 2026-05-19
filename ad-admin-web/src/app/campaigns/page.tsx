import Link from "next/link";
import { Campaign } from "@/types/ad";
import { StatusBadge } from "@/components/StatusBadge";
import { buttonVariants } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

async function getCampaigns(): Promise<Campaign[]> {
  const res = await fetch("http://localhost:3001/api/campaigns", {
    cache: "no-store",
  });
  return res.json();
}

export default async function CampaignsPage() {
  const campaigns = await getCampaigns();

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">캠페인 관리</h1>
        <Link href="/campaigns/new" className={buttonVariants()}>
          캠페인 생성
        </Link>
      </div>

      {campaigns.length === 0 ? (
        <div className="text-center py-16 text-muted-foreground">
          <p className="mb-4">등록된 캠페인이 없습니다.</p>
          <Link
            href="/campaigns/new"
            className={buttonVariants({ variant: "outline" })}
          >
            첫 캠페인 만들기
          </Link>
        </div>
      ) : (
        <div className="border rounded-lg">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-16">ID</TableHead>
                <TableHead>캠페인명</TableHead>
                <TableHead className="w-24">상태</TableHead>
                <TableHead className="w-32">시작일</TableHead>
                <TableHead className="w-32">종료일</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {campaigns.map((campaign) => (
                <TableRow key={campaign.id}>
                  <TableCell className="text-muted-foreground">
                    {campaign.id}
                  </TableCell>
                  <TableCell>
                    <Link
                      href={`/campaigns/${campaign.id}`}
                      className="text-primary hover:underline font-medium"
                    >
                      {campaign.name}
                    </Link>
                  </TableCell>
                  <TableCell>
                    <StatusBadge status={campaign.status} />
                  </TableCell>
                  <TableCell className="text-muted-foreground">
                    {campaign.startDate ?? "-"}
                  </TableCell>
                  <TableCell className="text-muted-foreground">
                    {campaign.endDate ?? "-"}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      )}
    </div>
  );
}
