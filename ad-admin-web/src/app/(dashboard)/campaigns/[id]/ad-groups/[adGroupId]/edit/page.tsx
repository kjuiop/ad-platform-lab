"use client";

import { useRouter, useParams } from "next/navigation";
import { useState, useEffect } from "react";
import { AdGroup, BidType } from "@/types/ad";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function EditAdGroupPage() {
  const router = useRouter();
  const params = useParams<{ id: string; adGroupId: string }>();
  const [name, setName] = useState("");
  const [bidType, setBidType] = useState<BidType>("CPC");
  const [bidAmount, setBidAmount] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`/api/ad-groups/${params.adGroupId}`)
      .then((res) => {
        if (!res.ok) throw new Error(`데이터를 불러오지 못했습니다. (${res.status})`);
        return res.json();
      })
      .then((data: AdGroup) => {
        setName(data.name);
        setBidType(data.bidType);
        setBidAmount(String(data.bidAmount));
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [params.adGroupId]);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError("");
    const res = await fetch(`/api/ad-groups/${params.adGroupId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name,
        bidType,
        bidAmount: Number(bidAmount),
      }),
    });
    if (!res.ok) {
      const data = await res.json();
      setError(data.error ?? "수정에 실패했습니다.");
      return;
    }
    router.push(`/campaigns/${params.id}/ad-groups/${params.adGroupId}`);
  }

  if (loading) {
    return <p className="text-muted-foreground text-sm">로딩 중...</p>;
  }

  return (
    <Card className="max-w-lg">
      <CardHeader>
        <CardTitle>광고그룹 수정</CardTitle>
      </CardHeader>
      <CardContent>
        {error && <p className="text-destructive text-sm mb-4">{error}</p>}
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="space-y-2">
            <Label htmlFor="name">광고그룹명 *</Label>
            <Input
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="bidType">입찰 방식 *</Label>
            <select
              id="bidType"
              value={bidType}
              onChange={(e) => setBidType(e.target.value as BidType)}
              className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-xs"
            >
              <option value="CPC">CPC (클릭당 과금)</option>
              <option value="CPM">CPM (1,000 노출당 과금)</option>
            </select>
          </div>
          <div className="space-y-2">
            <Label htmlFor="bidAmount">입찰가 (원) *</Label>
            <Input
              id="bidAmount"
              type="number"
              value={bidAmount}
              onChange={(e) => setBidAmount(e.target.value)}
              required
              min="1"
            />
          </div>
          <div className="flex gap-2 pt-2">
            <Button type="submit">저장</Button>
            <Button type="button" variant="outline" onClick={() => router.back()}>
              취소
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}
