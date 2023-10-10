export interface Project {
  id?: any;
  title?: string;
  description?: string;
  published?: boolean;
  slug?: string;
  createdAt?: Date;
  updatedAt?: Date;
  __v?: number;
  content?: string;
  category?: string;
  tags?: string[];
  author?: string;
  images?: string[];
  comments?: any[];
  likes?: any[];
  dislikes?: any[];
  views?: number;
  meta?: any;
  languages?: string[];
  url?: string;
}
