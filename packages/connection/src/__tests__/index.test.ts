import { describe, expect, it } from 'vitest'

describe('connection', () => {
  it('should be importable', async () => {
    const mod = await import('../index')
    expect(mod.Connection).toBeDefined()
  })
})
