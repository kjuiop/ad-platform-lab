import { NextRequest, NextResponse } from "next/server";
import { AdResponse } from "@/types/ad";

const mockAds: Record<string, AdResponse> = {
  "top-banner": {
    adId: "ad-001",
    title: "봄맞이 가전 세일 — 최대 50% 할인",
    description: "인기 가전제품을 특별 할인가에 만나보세요. 에어컨, 제습기, 공기청정기 외 다수.",
    imageUrl: null,
    clickUrl: "#",
  },
  "sidebar-1": {
    adId: "ad-002",
    title: "셀러 교육 프로그램",
    description: "초보 셀러를 위한 무료 온라인 강의. 상품 소싱부터 광고 운영까지.",
    imageUrl: null,
    clickUrl: "#",
  },
  "sidebar-2": {
    adId: "ad-003",
    title: "풀필먼트 서비스 무료 체험",
    description: "첫 달 물류비 무료! 전국 익일 배송으로 고객 만족도를 높이세요.",
    imageUrl: null,
    clickUrl: "#",
  },
  "feed-native-2": {
    adId: "ad-004",
    title: "신규 브랜드 입점 안내",
    description: "지금 입점하면 3개월 수수료 무료. 전담 매니저가 입점을 도와드립니다.",
    imageUrl: null,
    clickUrl: "#",
  },
  "feed-native-6": {
    adId: "ad-005",
    title: "AI 상품 설명 자동 생성 도구",
    description: "상품 사진만 올리면 AI가 상세 설명을 자동으로 작성합니다. 지금 무료 체험.",
    imageUrl: null,
    clickUrl: "#",
  },
  "feed-native-11": {
    adId: "ad-006",
    title: "여름 시즌 도매 특가전",
    description: "제습기, 선풍기, 냉감 침구 외 200여 종 도매가 최대 40% 할인.",
    imageUrl: null,
    clickUrl: "#",
  },
};

const defaultAd: AdResponse = {
  adId: "ad-default",
  title: "광고 문의",
  description: "이 자리에 광고를 게재하고 싶으시다면 문의해주세요.",
  imageUrl: null,
  clickUrl: "#",
};

export async function GET(request: NextRequest) {
  const slotId = request.nextUrl.searchParams.get("slotId") ?? "";
  const ad = mockAds[slotId] ?? defaultAd;
  return NextResponse.json(ad);
}
