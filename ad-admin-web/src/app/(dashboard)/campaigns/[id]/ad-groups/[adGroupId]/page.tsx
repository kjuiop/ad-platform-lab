import Link from "next/link";
import { getAppUrl } from "@/lib/api";
import { AdGroup, Creative } from "@/types/ad";
import { StatusBadge } from "@/components/StatusBadge";
import { StatusActions } from "@/components/StatusActions";
import { DeleteAdGroupButton } from "./DeleteAdGroupButton";
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

async function getAdGroup(adGroupId: string): Promise<AdGroup> {
  const res = await fetch(
    `${getAppUrl()}/api/ad-groups/${adGroupId}`,
    { cache: "no-store" },
  );
  if (!res.ok) throw new Error("AdGroup not found");
  return res.json();
}

async function getCreatives(adGroupId: string): Promise<Creative[]> {
  const res = await fetch(
    `${getAppUrl()}/api/ad-groups/${adGroupId}/creatives`,
    { cache: "no-store" },
  );
  return res.json();
}

export default async function AdGroupDetailPage({
  params,
}: {
  params: Promise<{ id: string; adGroupId: string }>;
}) {
  const { id, adGroupId } = await params;
  const [adGroup, creatives] = await Promise.all([
    getAdGroup(adGroupId),
    getCreatives(adGroupId),
  ]);

  return (
    <div>
      <div className="flex items-center gap-3 mb-1">
        <h1 className="text-2xl font-bold">{adGroup.name}</h1>
        <StatusBadge status={adGroup.status} />
      </div>
      <p className="text-sm text-muted-foreground mb-4">
        {adGroup.bidType} · {adGroup.bidAmount.toLocaleString()}원
      </p>
      <div className="flex gap-2 mb-6">
        <StatusActions
          status={adGroup.status}
          apiUrl={`/api/ad-groups/${adGroupId}/status`}
        />
        <Link
          href={`/campaigns/${id}/ad-groups/${adGroupId}/edit`}
          className={buttonVariants({ variant: "outline", size: "sm" })}
        >
          수정
        </Link>
        <DeleteAdGroupButton campaignId={id} adGroupId={adGroupId} />
      </div>

      <Separator className="mb-6" />

      <div className="flex justify-between items-center mb-4">
        <h2 className="text-lg font-semibold">소재</h2>
        <Link
          href={`/campaigns/${id}/ad-groups/${adGroupId}/creatives/new`}
          className={buttonVariants({ size: "sm" })}
        >
          소재 추가
        </Link>
      </div>

      {creatives.length === 0 ? (
        <div className="text-center py-12 text-muted-foreground">
          등록된 소재가 없습니다.
        </div>
      ) : (
        <div className="border rounded-lg">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-16">ID</TableHead>
                <TableHead>제목</TableHead>
                <TableHead>설명</TableHead>
                <TableHead className="w-24">상태</TableHead>
                <TableHead className="w-16"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {creatives.map((c) => (
                <TableRow key={c.id}>
                  <TableCell className="text-muted-foreground">
                    {c.id}
                  </TableCell>
                  <TableCell className="font-medium">{c.title}</TableCell>
                  <TableCell className="text-muted-foreground truncate max-w-xs">
                    {c.description ?? "-"}
                  </TableCell>
                  <TableCell>
                    <StatusBadge status={c.status} />
                  </TableCell>
                  <TableCell>
                    <Link
                      href={`/campaigns/${id}/ad-groups/${adGroupId}/creatives/${c.id}/edit`}
                      className="text-xs text-primary hover:underline"
                    >
                      수정
                    </Link>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      )}

      <div className="mt-6">
        <Link
          href={`/campaigns/${id}`}
          className="text-sm text-muted-foreground hover:underline"
        >
          ← 캠페인 상세
        </Link>
      </div>
    </div>
  );
}
