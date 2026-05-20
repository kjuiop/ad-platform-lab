import { Card, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import Link from "next/link";

export default function Home() {
  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">대시보드</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <Link href="/campaigns">
          <Card className="hover:shadow-md transition-shadow cursor-pointer">
            <CardHeader>
              <CardTitle className="text-base">캠페인 관리</CardTitle>
              <CardDescription>
                캠페인, 광고그룹, 소재를 등록하고 관리합니다.
              </CardDescription>
            </CardHeader>
          </Card>
        </Link>
        <Card className="opacity-50">
          <CardHeader>
            <CardTitle className="text-base">성과 리포트</CardTitle>
            <CardDescription>노출, 클릭, ROAS 확인 (준비중)</CardDescription>
          </CardHeader>
        </Card>
        <Card className="opacity-50">
          <CardHeader>
            <CardTitle className="text-base">예산 관리</CardTitle>
            <CardDescription>예산 설정 및 소진 현황 (준비중)</CardDescription>
          </CardHeader>
        </Card>
      </div>
    </div>
  );
}
