import { useEffect, useRef, useState } from 'react'
import { Input } from './ui/input'
import { Button } from './ui/button'
import { useQuery } from '@tanstack/react-query'
import { searchService, type Product } from '@/services/searchService'

export const Search = () => {
  const [query, setQuery] = useState('')
  const [debouncedQuery, setDebouncedQuery] = useState('')
  const [open, setOpen] = useState(false)
  const wrapperRef = useRef(null)

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedQuery(query)
    }, 500)

    return () => {
      clearTimeout(handler)
    }
  }, [query])

  const { data } = useQuery({
    queryKey: ['products', debouncedQuery],
    queryFn: async (): Promise<Product[]> =>
      await searchService.getProductsByQuery(debouncedQuery),
    enabled: debouncedQuery.length > 0,
    refetchOnWindowFocus: false,
  })

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        wrapperRef.current &&
        !(wrapperRef.current as HTMLElement).contains(event.target as Node)
      ) {
        setOpen(false)
      }
    }

    document.addEventListener('mousedown', handleClickOutside)
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [])

  const filtered = data
    ? data.filter((product) =>
        product.name.toLowerCase().includes(debouncedQuery.toLowerCase()),
      )
    : []

  return (
    <div className="relative w-full max-w-md" ref={wrapperRef}>
      <div className="flex items-center gap-2 w-full">
        <Input
          type="search"
          placeholder="Search..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => setOpen(true)}
        />
        <Button>Search</Button>
      </div>

      {open && filtered.length > 0 && (
        <div className="absolute top-full left-0 mt-1 w-full bg-white border rounded-md shadow z-10">
          {filtered.map((x, i) => (
            <div
              className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
              key={i}
              onClick={() => {
                setQuery(x.name)
                setOpen(false)
              }}
            >
              {x.name}
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
