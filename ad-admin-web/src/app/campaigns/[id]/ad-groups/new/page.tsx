"use client";

import { useRouter, useParams } from "next/navigation";
import { useState } from "react";
import { BidType } from "@/types/ad";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function NewAdGroupPage() {
  const router = useRouter();
  const params = useParams<{ id: string }>();
  const [name, setName] = useState("");
  const [bidType, setBidType] = useState<BidType>("CPC");
  const [bidAmount, setBidAmount] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError("");
    const res = await fetch(`/api/campaigns/${params.id}/ad-groups`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name,
        bidType,
        bidAmount: Number(bidAmount),
      }),
    });
    if (!res.ok) {
      const data = await res.json();
      setError(data.error ?? "생성에 실패했습니다.");
      return;
    }
    router.push(`/campaigns/${params.id}`);
  }

  return (
    <Card className="max-w-lg">
      <CardHeader>
        <CardTitle>광고그룹 생성</CardTitle>
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
              placeholder="예: 20대 여성 타게팅"
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
              placeholder="예: 500"
            />
          </div>
          <div className="flex gap-2 pt-2">
            <Button type="submit">생성</Button>
            <Button type="button" variant="outline" onClick={() => router.back()}>
              취소
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}
