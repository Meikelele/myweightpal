interface WeightEntryFormValue {
  date: Date | null;
  weight: number | null;
  tookCreatine: boolean;
  note: string;
}

interface CreateWeightEntryRequest {
  date: string;
  weight: number;
  tookCreatine: boolean;
  note: string | null;
  userId: number;
}
