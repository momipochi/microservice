import { apiFetch } from '@/lib/api'

export const searchService = {
  getProductsByQuery: async (query: string): Promise<Product[]> => {
    const res: Product[] = await apiFetch(`/search?query=${query}`)
    return res
  },
}

export interface Product {
  id: string
  name: string
  description?: string
  price: number
}
