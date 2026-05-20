"use client";

import Link from "next/link";
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default function FindAccountPage() {
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [result, setResult] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError("");
    setResult("");

    const res = await fetch("/api/auth/find-account", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, phone }),
    });

    if (!res.ok) {
      const data = await res.json();
      setError(data.error ?? "일치하는 계정을 찾을 수 없습니다.");
      return;
    }

    const data = await res.json();
    setResult(data.email);
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-muted/30">
      <Card className="w-full max-w-sm">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl">아이디 찾기</CardTitle>
          <CardDescription>
            가입 시 등록한 이름과 연락처를 입력해주세요
          </CardDescription>
        </CardHeader>
        <CardContent>
          {error && (
            <div className="mb-4 p-3 bg-destructive/10 text-destructive text-sm rounded-md">
              {error}
            </div>
          )}
          {result ? (
            <div className="text-center py-4">
              <p className="text-sm text-muted-foreground mb-2">
                등록된 이메일
              </p>
              <p className="text-lg font-medium">{result}</p>
              <Link
                href="/login"
                className="inline-block mt-6 text-sm text-primary hover:underline"
              >
                로그인하러 가기
              </Link>
            </div>
          ) : (
            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <div className="space-y-2">
                <Label htmlFor="name">이름</Label>
                <Input
                  id="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                  placeholder="홍길동"
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="phone">연락처</Label>
                <Input
                  id="phone"
                  type="tel"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                  required
                  placeholder="010-1234-5678"
                />
              </div>
              <Button type="submit" className="w-full mt-2">
                아이디 찾기
              </Button>
            </form>
          )}

          <div className="mt-6 border-t pt-4 text-center">
            <Link
              href="/login"
              className="text-sm text-muted-foreground hover:underline"
            >
              로그인으로 돌아가기
            </Link>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
