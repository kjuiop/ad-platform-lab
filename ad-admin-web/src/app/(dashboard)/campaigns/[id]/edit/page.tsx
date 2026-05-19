"use client";

import { useRouter, useParams } from "next/navigation";
import { useState, useEffect } from "react";
import { Campaign } from "@/types/ad";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function EditCampaignPage() {
  const router = useRouter();
  const params = useParams<{ id: string }>();
  const [name, setName] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`/api/campaigns/${params.id}`)
      .then((res) => res.json())
      .then((data: Campaign) => {
        setName(data.name);
        setStartDate(data.startDate ?? "");
        setEndDate(data.endDate ?? "");
        setLoading(false);
      });
  }, [params.id]);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError("");
    const res = await fetch(`/api/campaigns/${params.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name,
        startDate: startDate || null,
        endDate: endDate || null,
      }),
    });
    if (!res.ok) {
      const data = await res.json();
      setError(data.error ?? "수정에 실패했습니다.");
      return;
    }
    router.push(`/campaigns/${params.id}`);
  }

  if (loading) {
    return <p className="text-muted-foreground text-sm">로딩 중...</p>;
  }

  return (
    <Card className="max-w-lg">
      <CardHeader>
        <CardTitle>캠페인 수정</CardTitle>
      </CardHeader>
      <CardContent>
        {error && <p className="text-destructive text-sm mb-4">{error}</p>}
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="space-y-2">
            <Label htmlFor="name">캠페인명 *</Label>
            <Input
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="startDate">시작일</Label>
            <Input
              id="startDate"
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="endDate">종료일</Label>
            <Input
              id="endDate"
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
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
