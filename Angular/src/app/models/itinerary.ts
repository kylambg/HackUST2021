export interface Country{
  id: number;
  name: string;
  iso_code: string;
}

export interface Region{
  id: number;
  name: string;
  safety_level: 'safe' | 'low' | 'medium' | 'high';
  country: Country;
}

export interface City{
  id: number;
  name: string;
  airport_code: string;
  region: Region;
}

export interface PastTravel{
  city: City;
  last_date: Date | string;
}

export interface OriginDetails{
  leaving_from: City | undefined;
  departure_date: Date | string;
  past_travels: PastTravel[];
}

export interface Destination{
  city: City;
  date: Date | string;
}

export interface Itinerary{
  id: number;
  status: 'origin' | 'destination' | 'plan';
  created_at: string;
  origin_details?: OriginDetails;
  destinations?: Destination[];
  plan?: any;
}
