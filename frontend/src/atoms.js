import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const LoginState = atom({
  key: "LoginState",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const LineFilterState = atom({
  key: "LineFilterState",
  default: "ALL",
});

export const TierFilterState = atom({
  key: "TierFilterState",
  default: "ALL",
});
