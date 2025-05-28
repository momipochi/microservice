import { apiFetch } from '@/lib/api'

export interface ProductDTO {
  id: string
  name: string
  description: string
  price: number
}

export const listingSearch = {
  getListingsByQuery: async (query: string): Promise<ProductDTO[]> => {
    const res: ProductDTO[] = await apiFetch(`/listings?query=${query}`)
    return res
  },
}
